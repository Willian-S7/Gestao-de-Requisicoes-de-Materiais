package com.wil.RequisicoesMateriais.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public record ItemRequisicaoRequest(
    @Positive 
    Long requisicaoId,
    @Positive 
    Long materialId,
    @Positive 
    int quantidade,
    @Positive 
    BigDecimal precoUni
) {

}
