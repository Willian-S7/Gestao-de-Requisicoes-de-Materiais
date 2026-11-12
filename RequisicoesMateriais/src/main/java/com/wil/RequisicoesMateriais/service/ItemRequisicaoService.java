package com.wil.RequisicoesMateriais.service;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.RequisicoesMateriais.dto.request.ItemRequisicaoRequest;
import com.wil.RequisicoesMateriais.model.ItemRequisicao;
import com.wil.RequisicoesMateriais.model.Material;
import com.wil.RequisicoesMateriais.model.Requisicao;

@Service 
public class ItemRequisicaoService {

    private final MaterialService materialService;

    public ItemRequisicaoService (MaterialService materialService) {

        this.materialService = materialService;
    }

    public ItemRequisicao criarItem(ItemRequisicaoRequest request, Requisicao requisicao) {
        Material material = materialService.buscarMaterialporId(request.materialId());

        if (material.getQuantidadeEstoque() < request.quantidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente");
        }

        if(!material.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Material inativo");
        }

        BigDecimal valorTotal = material.getPrecoUni().multiply(BigDecimal.valueOf(request.quantidade()));
        
        ItemRequisicao item = new ItemRequisicao();
        item.setMaterial(material);
        item.setRequisicao(requisicao);
        item.setQuantidade(request.quantidade());
        item.setPrecoUni(material.getPrecoUni());
        item.setPrecoTotal(valorTotal);
        return item;
    }

}
