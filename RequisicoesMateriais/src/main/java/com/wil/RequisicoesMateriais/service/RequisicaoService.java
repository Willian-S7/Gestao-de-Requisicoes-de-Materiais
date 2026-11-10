package com.wil.RequisicoesMateriais.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.RequisicoesMateriais.dto.request.ItemRequisicaoRequest;
import com.wil.RequisicoesMateriais.dto.request.RequisicaoRequest;
import com.wil.RequisicoesMateriais.dto.response.RequisicaoResponse;
import com.wil.RequisicoesMateriais.model.Colaborador;
import com.wil.RequisicoesMateriais.model.ItemRequisicao;
import com.wil.RequisicoesMateriais.model.Material;
import com.wil.RequisicoesMateriais.model.Requisicao;
import com.wil.RequisicoesMateriais.model.StatusRequisicao;
import com.wil.RequisicoesMateriais.repository.MaterialRepository;
import com.wil.RequisicoesMateriais.repository.RequisicaoRepository;

import jakarta.transaction.Transactional;

@Service 
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;
    private final ItemRequisicaoService itemRequisicaoService;
    private final ColaboradorService colaboradorService;
    private final MaterialRepository materialRepository;


    public RequisicaoService (RequisicaoRepository requisicaoRepository, ItemRequisicaoService itemRequisicaoService, ColaboradorService colaboradorService, MaterialRepository materialRepository) {
        this.requisicaoRepository = requisicaoRepository;
        this.itemRequisicaoService = itemRequisicaoService;
        this.colaboradorService = colaboradorService;
        this.materialRepository =  materialRepository;
    }

    public RequisicaoResponse toDTO (Requisicao requisicao) {
        RequisicaoResponse requisicaoDTO = new RequisicaoResponse(
            requisicao.getId(), requisicao.getColaborador().getId(),
            requisicao.getColaborador().getNome(), requisicao.getStatus(),
            requisicao.getValorTotal(), requisicao.getDataCriacao()
        );
        return requisicaoDTO;
    }

    @Transactional 
    public RequisicaoResponse solicitarRequisicao (RequisicaoRequest request) {

        Colaborador colaborador = colaboradorService.colaboradorPorID(request.id());

        if(!colaborador.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Colaborador Inativado");
        }

        Requisicao novaRequisicao = new Requisicao();

        for (ItemRequisicaoRequest itemRequisicao : request.itens()) {
            ItemRequisicao item = itemRequisicaoService.criarItem(itemRequisicao, novaRequisicao);

            Material material = item.getMaterial();

            if (material.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente");
            }   
            
            material.setQuantidadeEstoque(material.getQuantidadeEstoque() - item.getQuantidade());

            novaRequisicao.getItens().add(item);
            materialRepository.save(material);
        }

        BigDecimal valorTotal = novaRequisicao.getItens().stream()
            .map(item -> item.getPrecoUni().multiply(BigDecimal.valueOf(item.getQuantidade())))
            .reduce(BigDecimal.ZERO, (total, valorSubTotal) -> total.add(valorSubTotal));

        novaRequisicao.setColaborador(colaborador);
        novaRequisicao.setDataCriacao(LocalDateTime.now());
        novaRequisicao.setStatus(StatusRequisicao.SOLICITADA);
        novaRequisicao.setValorTotal(valorTotal);


        requisicaoRepository.save(novaRequisicao);

        return toDTO(novaRequisicao);
    }

    @Transactional
    public RequisicaoResponse atenderRequisicao(Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requisição não encontrada"));

        if(requisicao.getStatus() != StatusRequisicao.APROVADA) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Somente requisições aprovadas podem ser atendidas");
        }

        requisicao.setStatus(StatusRequisicao.ATENDIDA);
        requisicaoRepository.save(requisicao);
        return toDTO(requisicao);
    }

    @Transactional
    public RequisicaoResponse cancelarRequisicao(Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requisição não encontrada"));    

        if(requisicao.getStatus() == StatusRequisicao.ATENDIDA || requisicao.getStatus() == StatusRequisicao.CANCELADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requisição não pode ser cancelada");
        }

        requisicao.setStatus(StatusRequisicao.CANCELADA);

        requisicao.getItens().forEach(req -> {
            Material material = req.getMaterial();
            material.setQuantidadeEstoque(material.getQuantidadeEstoque() + req.getQuantidade());
            materialRepository.save(material);
        });

        requisicaoRepository.save(requisicao);
        return toDTO(requisicao);        
    }

    @Transactional
    public RequisicaoResponse aprovarRequisicao(Long id) {
       Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requisição não encontrada"));    

        if(requisicao.getStatus() != StatusRequisicao.SOLICITADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Somente requisições com status SOLICITADA podem ser atendidas");
        }

        requisicao.setStatus(StatusRequisicao.APROVADA);
        requisicaoRepository.save(requisicao);
        return toDTO(requisicao);
    }

    public List<RequisicaoResponse> porStatus(StatusRequisicao status) {
        return requisicaoRepository.findByStatus(status)
        .stream()
        .map(req -> toDTO(req))
        .toList();
    }
    public List<RequisicaoResponse> porColadoradorId(Long colaboradorId) {
        return requisicaoRepository.findByColadoradorId(colaboradorId)
        .stream()
        .map(req -> toDTO(req))
        .toList();
    } 

    public List<RequisicaoResponse> porPeriodo(LocalDateTime dataInicio, LocalDateTime dataFinal) {
        return requisicaoRepository.findByDataCriacaoBetween(dataInicio, dataFinal)
        .stream()
        .map(req -> toDTO(req))
        .toList();
    }
}
