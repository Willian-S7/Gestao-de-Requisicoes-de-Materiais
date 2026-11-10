package com.wil.RequisicoesMateriais.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequisicaoRequest(
    @NotBlank 
    Long id,
    @NotNull 
    List<ItemRequisicaoRequest> itens
) {

}
