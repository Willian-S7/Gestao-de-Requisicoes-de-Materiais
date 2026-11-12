package com.wil.RequisicoesMateriais.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wil.RequisicoesMateriais.dto.request.ColaboradorRequest;
import com.wil.RequisicoesMateriais.dto.response.ColaboradorResponse;
import com.wil.RequisicoesMateriais.model.Colaborador;
import com.wil.RequisicoesMateriais.repository.ColaboradorRepository;

@ExtendWith (MockitoExtension.class)
public class ColaboradorServiceTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks 
    private ColaboradorService colaboradorService;

    @Test 
    void criarColaboradorTest () {
        ColaboradorRequest request = new ColaboradorRequest("nome", "email@email.com");

        when(colaboradorRepository.save(any(Colaborador.class))).thenAnswer(colab -> colab.getArgument(0));

        ColaboradorResponse response = colaboradorService.criarColaborador(request);

        assertEquals("nome", response.nome());
    }

    @Test 
    void alterarColaboradorTest() {
        Long id = 1L;
        ColaboradorRequest request = new ColaboradorRequest("nome", "email@email.com");
        Colaborador colaborador =  new Colaborador();

        when(colaboradorRepository.findById(id))
            .thenReturn(Optional.of(colaborador));

        when(colaboradorRepository.save(any(Colaborador.class))).thenAnswer(colab -> colab.getArgument(0));

        ColaboradorResponse response = colaboradorService.alterarColaborador(id, request);

        assertEquals("nome", response.nome());
    }

    @Test
    void desligarColaboradorTest() {
        Long id = 1L;
        boolean ativo = false;

        Colaborador colaborador = new Colaborador();
        colaborador.setAtivo(true);

        when(colaboradorRepository.findById(id)).thenReturn(Optional.of(colaborador));

        ColaboradorResponse response = colaboradorService.desligarColaborador(id, ativo);

        assertFalse(response.ativo());
    }
}
