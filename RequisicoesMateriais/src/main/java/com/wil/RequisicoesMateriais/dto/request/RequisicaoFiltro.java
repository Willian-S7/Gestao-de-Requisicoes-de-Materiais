package com.wil.RequisicoesMateriais.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wil.RequisicoesMateriais.model.StatusRequisicao;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RequisicaoFiltro(
    @Positive 
    Long colaboradorId,
    String colaboradorNome,
    StatusRequisicao status,
    LocalDate dataInicio,
    LocalDate dataFim,
    @PositiveOrZero 
    BigDecimal valorMin,
    @PositiveOrZero
    BigDecimal valorMax
) {

}
