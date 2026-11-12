package com.wil.RequisicoesMateriais.dto.request;

import jakarta.validation.constraints.Positive;
public record ItemRequisicaoRequest(
    @Positive 
    Long requisicaoId,
    @Positive 
    Long materialId,
    @Positive 
    int quantidade
) {

}
