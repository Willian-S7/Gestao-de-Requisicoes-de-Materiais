package com.wil.RequisicoesMateriais.dto.response;

import java.time.LocalDateTime;

public record ColaboradorResponse(
    Long id,
    String nome,
    String email,
    boolean ativo,
    LocalDateTime dataCadastro
) {

}
