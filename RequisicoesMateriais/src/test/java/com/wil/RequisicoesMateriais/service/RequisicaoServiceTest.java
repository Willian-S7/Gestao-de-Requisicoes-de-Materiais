package com.wil.RequisicoesMateriais.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import com.wil.RequisicoesMateriais.dto.request.RequisicaoRequest;
import com.wil.RequisicoesMateriais.dto.response.RequisicaoResponse;
import com.wil.RequisicoesMateriais.model.Colaborador;
import com.wil.RequisicoesMateriais.model.Requisicao;
import com.wil.RequisicoesMateriais.model.StatusRequisicao;
import com.wil.RequisicoesMateriais.repository.MaterialRepository;
import com.wil.RequisicoesMateriais.repository.RequisicaoRepository;


@ExtendWith (MockitoExtension.class)
class RequisicaoServiceTest {

    @Mock
    private  ItemRequisicaoService itemRequisicaoService;

    @Mock
    private  ColaboradorService colaboradorService;

    @Mock
    private  MaterialRepository materialRepository;

    @Mock
    private  RequisicaoRepository requisicaoRepository;

    @InjectMocks 
    private RequisicaoService requisicaoService;
    @Test 
    void solicitarReqTest() {
        Long id = 1L;
        RequisicaoRequest request = new RequisicaoRequest(id, List.of());

        Colaborador colaborador = new Colaborador();
        colaborador.setAtivo(true);
        colaborador.setNome("nome");

        when (colaboradorService.colaboradorPorID(id)).thenReturn(colaborador);

        when (requisicaoRepository.save(any(Requisicao.class))).thenAnswer(req -> req.getArgument(0));

        RequisicaoResponse response = requisicaoService.solicitarRequisicao(request);

        assertEquals("nome", response.colaboradorNome());
    }

    @Test
    void cancelarReqTest() {
        Long id = 1L;

        Colaborador colaborador = new Colaborador();
        colaborador.setId(2L);
        colaborador.setNome("nome");

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(StatusRequisicao.SOLICITADA);
        requisicao.setColaborador(colaborador);

        when (requisicaoRepository.findById(id)).thenReturn(Optional.of(requisicao));
 
        
        RequisicaoResponse response = requisicaoService.cancelarRequisicao(id);

        assertEquals(StatusRequisicao.CANCELADA, response.status());
    }

    @Test 
    void aprovarReqTest() {
        Long id = 1L;

        Colaborador colaborador = new Colaborador();
        colaborador.setId(2L);
        colaborador.setNome("nome");

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(StatusRequisicao.SOLICITADA);
        requisicao.setColaborador(colaborador);

        when (requisicaoRepository.findById(id)).thenReturn(Optional.of(requisicao));

        RequisicaoResponse response = requisicaoService.aprovarRequisicao(id);

        assertEquals(StatusRequisicao.APROVADA, response.status());
    }

    @Test 
    void atenderReqTest() {
        Long id = 1L;

        Colaborador colaborador = new Colaborador();
        colaborador.setId(3L);
        colaborador.setNome("nome");

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(StatusRequisicao.APROVADA);
        requisicao.setColaborador(colaborador);

        when (requisicaoRepository.findById(id)).thenReturn(Optional.of(requisicao));

        RequisicaoResponse response = requisicaoService.atenderRequisicao(id);

        assertEquals(StatusRequisicao.ATENDIDA, response.status());
    }    
}
