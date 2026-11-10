package com.wil.RequisicoesMateriais.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wil.RequisicoesMateriais.repository.MaterialRepository;
import com.wil.RequisicoesMateriais.repository.RequisicaoRepository;

@ExtendWith (MockitoExtension.class)
class RequisicaoServiceTest {

    @Mock 
    private  RequisicaoRepository requisicaoRepository;

    @Mock
    private  ItemRequisicaoService itemRequisicaoService;

    @Mock
    private  ColaboradorService colaboradorService;

    @Mock
    private  MaterialRepository materialRepository;

    @InjectMocks
    private RequisicaoService requisicaoService;

    @Test 
    void AtenderRequisicaoFalha() {}

}
