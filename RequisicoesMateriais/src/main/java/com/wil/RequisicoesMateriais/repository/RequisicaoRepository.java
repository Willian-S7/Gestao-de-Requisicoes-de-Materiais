package com.wil.RequisicoesMateriais.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wil.RequisicoesMateriais.model.Requisicao;
import com.wil.RequisicoesMateriais.model.StatusRequisicao;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long>, JpaSpecificationExecutor<Requisicao>{

    List<Requisicao> findByStatus(StatusRequisicao status);

    List<Requisicao> findByColadoradorId(Long colaboradorId);

    List<Requisicao> findByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFinal);

}
