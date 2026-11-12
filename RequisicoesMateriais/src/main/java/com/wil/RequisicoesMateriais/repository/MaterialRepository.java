package com.wil.RequisicoesMateriais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wil.RequisicoesMateriais.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Long>{

    List<Material> findByNome (String nomeProduto);

    List<Material> findByAtivoTrue();

    List<Material> findByAtivoFalse();

    @Query("""
        SELECT m FROM Material m WHERE m.quantidadeEstoque <= m.estoqueMinimo""")
    List<Material> findMateriaisComEstoqueBaixo();
}
