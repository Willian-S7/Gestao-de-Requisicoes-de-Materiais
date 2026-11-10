package com.wil.RequisicoesMateriais.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wil.RequisicoesMateriais.dto.request.RequisicaoRequest;
import com.wil.RequisicoesMateriais.dto.response.RequisicaoResponse;
import com.wil.RequisicoesMateriais.model.StatusRequisicao;
import com.wil.RequisicoesMateriais.service.RequisicaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/requisicoes")
public class RequisicaoController {

    private final RequisicaoService requisicaoService;

    public RequisicaoController (RequisicaoService requisicaoService) {
        this.requisicaoService = requisicaoService;
    }

    @PostMapping
    public ResponseEntity<RequisicaoResponse> solicitarRequisicao(@RequestBody @Valid RequisicaoRequest request) {
        RequisicaoResponse response = requisicaoService.solicitarRequisicao(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/atender/{id}")
    public ResponseEntity<RequisicaoResponse> atenderRequisicao(@PathVariable Long id) {
        RequisicaoResponse requisicao = requisicaoService.atenderRequisicao(id);
        return ResponseEntity.ok(requisicao);
    }

    @PatchMapping ("/cancelar/{id}")
    public ResponseEntity<RequisicaoResponse> cancelarRequisicao(@PathVariable Long id) {
        RequisicaoResponse response = requisicaoService.cancelarRequisicao(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/aprovar/{id}")
    public ResponseEntity<RequisicaoResponse> aprovarRequisicao(@PathVariable Long id) {
        RequisicaoResponse response = requisicaoService.aprovarRequisicao(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/status/{status}")
    public List<RequisicaoResponse> porStatus(@PathVariable StatusRequisicao status) {
        return requisicaoService.porStatus(status);
    }

    @GetMapping ("/colaborador/{colaboradorId}")
    public List<RequisicaoResponse> porColaborador(@PathVariable Long colaboradorId) {
        return requisicaoService.porColadoradorId(colaboradorId);
    }

    @GetMapping ("/periodo")
    public List<RequisicaoResponse> porPeriodo(@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
    @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return requisicaoService.porPeriodo(dataInicio, dataFinal);
    }
}
