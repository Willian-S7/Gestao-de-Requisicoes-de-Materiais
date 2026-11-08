package com.wil.RequisicoesMateriais.dto.request;

import com.wil.RequisicoesMateriais.model.StatusRequisicao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequisicaoRequest(
    @NotBlank 
    Long colaboradorId,
    @NotNull 
    StatusRequisicao status
) {

}
