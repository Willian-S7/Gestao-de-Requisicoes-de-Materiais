package com.wil.RequisicoesMateriais.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wil.RequisicoesMateriais.model.Colaborador;

import jakarta.transaction.Transactional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{

    List<Colaborador> findByAtivoTrue();

    List<Colaborador> findByAtivoFalse();

    @Transactional 
    void deleteByAtivoFalseAndDataDesativacaoBefore(LocalDateTime limite);

    List<Colaborador> findByNome(String nome);
}
