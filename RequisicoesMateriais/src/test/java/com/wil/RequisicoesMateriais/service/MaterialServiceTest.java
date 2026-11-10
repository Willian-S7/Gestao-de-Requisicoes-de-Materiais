package com.wil.RequisicoesMateriais.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void materialTest() {
        Material material = new Material();
        material.setNome("Teclado");

        when(materialRepository.findByNome("Teclado")).thenReturn(List.of(material));

        List<MaterialResponse> resultado = materialService.porNome("Teclado");

        assertEquals(1, resultado.size());
        
         verify(materialRepository).findByNome("Teclado");

    }
}
