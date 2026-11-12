package com.wil.RequisicoesMateriais.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wil.RequisicoesMateriais.model.StatusRequisicao;

public record RequisicaoResponse(
    Long id,
    Long colaboradorId,
    String colaboradorNome,
    StatusRequisicao status,
    BigDecimal valorTotal,
    LocalDateTime dataCriacao

) {

}
