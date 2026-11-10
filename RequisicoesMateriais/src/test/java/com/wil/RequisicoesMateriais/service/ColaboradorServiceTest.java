package com.wil.RequisicoesMateriais.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wil.RequisicoesMateriais.repository.ColaboradorRepository;

@ExtendWith (MockitoExtension.class)
public class ColaboradorServiceTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks 
    private ColaboradorService colaboradorService;

    @Test 
    void colaboradorTest () {}
}
