package com.wil.RequisicoesMateriais.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wil.RequisicoesMateriais.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Long>{

}
