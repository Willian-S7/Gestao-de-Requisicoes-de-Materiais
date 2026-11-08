package com.wil.RequisicoesMateriais.dto.response;

import java.math.BigDecimal;

public record MaterialResponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal precoUnitario,
    int quantidadeEstoque,
    int estoqueMinimo,
    boolean ativo
) {

}
