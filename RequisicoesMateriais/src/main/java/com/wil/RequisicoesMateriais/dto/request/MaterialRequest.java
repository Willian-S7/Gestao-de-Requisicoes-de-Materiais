package com.wil.RequisicoesMateriais.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record MaterialRequest(
    @NotBlank 
    String nome,
    @NotBlank 
    String descricao,
    @NotBlank 
    BigDecimal precoUni,
    @PositiveOrZero 
    int quantidadeEstoque,
    @PositiveOrZero 
    int estoqueMinimo
) {

}
