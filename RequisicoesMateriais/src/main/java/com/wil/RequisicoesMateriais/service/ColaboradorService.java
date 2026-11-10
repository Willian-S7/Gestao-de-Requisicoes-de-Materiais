package com.wil.RequisicoesMateriais.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.RequisicoesMateriais.dto.request.ColaboradorRequest;
import com.wil.RequisicoesMateriais.dto.response.ColaboradorResponse;
import com.wil.RequisicoesMateriais.model.Colaborador;
import com.wil.RequisicoesMateriais.repository.ColaboradorRepository;

@Service 
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService (ColaboradorRepository colaboradorRepository){
        this.colaboradorRepository = colaboradorRepository;
    }

    public ColaboradorResponse toDTO(Colaborador colaborador) {
        return new ColaboradorResponse(
            colaborador.getId(), colaborador.getNome(), colaborador.getEmail(), colaborador.isAtivo(), colaborador.getDataCadastro()
        );
    }

    public ColaboradorResponse criarColaborador(ColaboradorRequest request) {
        Colaborador colaborador = new Colaborador();
        colaborador.setNome(request.nome());
        colaborador.setEmail(request.email());
        colaborador.setDataCadastro(LocalDateTime.now());
        colaborador.setAtivo(true);
        colaboradorRepository.save(colaborador);
        return toDTO(colaborador);
    }

    public List<ColaboradorResponse> listarColaboradores(){
        return colaboradorRepository.findByAtivoTrue().stream().map(colaborador -> toDTO(colaborador)).toList();
    }

    public Colaborador colaboradorPorID(Long id) {
        return colaboradorPorID(id);
    }

    public ColaboradorResponse buscarColaboradorResponse(Long id) {
        Colaborador colaborador = colaboradorPorID(id);
        return toDTO(colaborador);
    }

    public ColaboradorResponse alterarColaborador (Long id, ColaboradorRequest request) {
        Colaborador colaborador = colaboradorPorID(id);
        colaborador.setNome(request.nome());
        colaborador.setEmail(request.email());

        return toDTO(colaborador);
    }

    public ColaboradorResponse desligarColaborador(Long id, boolean ativo){
        Colaborador colaborador = colaboradorPorID(id);
        if (colaborador.isAtivo() == ativo){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status já está de acordo");
        }
        colaborador.setAtivo(ativo);
        return toDTO(colaborador);     
    }

    public void deletarPorStatus (){
        LocalDateTime limite = LocalDateTime.now().minusDays(30);
        colaboradorRepository.deleteByAtivoFalseAndDataDesativacaoBefore(limite);
    }

    public List<ColaboradorResponse> colaboradoresAtivos(){
        return colaboradorRepository.findByAtivoTrue()
            .stream()
            .map(colaborador -> toDTO(colaborador))
            .toList();
    }

        public List<ColaboradorResponse> colaboradoresInativos(){
        return colaboradorRepository.findByAtivoFalse()
            .stream()
            .map(colaborador -> toDTO(colaborador))
            .toList();
    }

        public List<ColaboradorResponse> porNome(String nome){
        return colaboradorRepository.findByNome(nome)
            .stream()
            .map(colaborador -> toDTO(colaborador))
            .toList();
    }
}
