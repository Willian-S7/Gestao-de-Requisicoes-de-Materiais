package com.wil.RequisicoesMateriais.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wil.RequisicoesMateriais.dto.request.MaterialRequest;
import com.wil.RequisicoesMateriais.dto.response.MaterialResponse;
import com.wil.RequisicoesMateriais.model.Material;
import com.wil.RequisicoesMateriais.repository.MaterialRepository;
 

@ExtendWith (MockitoExtension.class)
class MaterialServiceTest {

    @Mock 
    private MaterialRepository materialRepository;

    @InjectMocks 
    private MaterialService materialService;

    @Test 
    void materialPorNomeTest() {
        Material material = new Material();
        material.setNome("Teclado");

        when(materialRepository.findByNome("Teclado")).thenReturn(List.of(material));

        List<MaterialResponse> resultado = materialService.porNome("Teclado");

        assertEquals(1, resultado.size());

        verify(materialRepository).findByNome("Teclado");

    }

    @Test
    void materialCriarTest() {
        MaterialRequest request = new MaterialRequest("nome", "descricao", new BigDecimal(10), 5, 4);

    when(materialRepository.save(any(Material.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    MaterialResponse response = materialService.criarNovoMaterial(request);

    assertEquals("nome", response.nome());
    }

    @Test 
    void materialAlterarTest(){
        MaterialRequest request = new MaterialRequest("nome", "descri", new BigDecimal(10), 10, 5);

        when(materialRepository.save(any(Material.class)))
            .thenAnswer(mat -> mat.getArgument(0));

        MaterialResponse response = materialService.criarNovoMaterial(request);

        assertEquals("descri", response.descricao());
    }

    @Test
    void materialDeleteTest() {
        Long id = 1L;
        Material materialDelete = new Material();
        
        when(materialRepository.findById(id))
            .thenReturn(Optional.of(materialDelete));

        materialService.deleteMaterial(id);

        verify (materialRepository).delete(materialDelete);
    }
}
