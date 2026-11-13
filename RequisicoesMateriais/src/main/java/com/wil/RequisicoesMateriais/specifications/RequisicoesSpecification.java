package com.wil.RequisicoesMateriais.specifications;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.wil.RequisicoesMateriais.model.Requisicao;
import com.wil.RequisicoesMateriais.model.StatusRequisicao;

public class RequisicoesSpecification {

    public static Specification<Requisicao> porColaboradorId(Long colaboradorId) {
        return (root, query, cb) -> cb.equal(root.get("colaborador").get("id"), colaboradorId);
    }

    public static Specification<Requisicao> porColaboradorNome(String colaboradorNome) {
        return (root, query, cb) -> cb.equal(root.get("colaborador").get("nome"), colaboradorNome);
    }

    public static Specification<Requisicao> porStatus(StatusRequisicao status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Requisicao> aPartirDe(LocalDate dataInicio) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicio.atStartOfDay());
    }

    public static Specification<Requisicao> ateAData(LocalDate dataFim) {
        return (root, query, cb) -> cb.lessThan(root.get("dataCriacao"), dataFim.atStartOfDay().plusDays(1));
    }

    public static Specification<Requisicao> apartirValorMin(BigDecimal valorMin) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("valorTotal"), valorMin);
    }

    public static Specification<Requisicao> ateValorMax(BigDecimal valorMax) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("valorTotal"), valorMax);
    }

}
