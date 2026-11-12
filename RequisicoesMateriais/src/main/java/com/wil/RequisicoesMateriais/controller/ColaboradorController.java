package com.wil.RequisicoesMateriais.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wil.RequisicoesMateriais.dto.request.ColaboradorRequest;
import com.wil.RequisicoesMateriais.dto.response.ColaboradorResponse;
import com.wil.RequisicoesMateriais.service.ColaboradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/colaboradores")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    public ColaboradorController (ColaboradorService colaboradorService){
        this.colaboradorService = colaboradorService;
    }

    @PostMapping
    public ResponseEntity<ColaboradorResponse> adicionarColaborador(@RequestBody @Valid ColaboradorRequest request) {
        ColaboradorResponse colaborador = colaboradorService.criarColaborador(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(colaborador);
    }

    @GetMapping
    public ResponseEntity<List<ColaboradorResponse>> listarColaboradores(){
        List<ColaboradorResponse> colaboradores = colaboradorService.listarColaboradores();
        return ResponseEntity.ok(colaboradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorResponse> buscarColaborador(@PathVariable Long id) {
        ColaboradorResponse colaborador = colaboradorService.buscarColaboradorResponse(id);
        return ResponseEntity.ok(colaborador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColaboradorResponse> alterarColaborador(@PathVariable Long id, @RequestBody @Valid ColaboradorRequest request) {
        ColaboradorResponse colaborador = colaboradorService.alterarColaborador(id, request);
        return ResponseEntity.ok(colaborador);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ColaboradorResponse> desligarLigarColaborador (@PathVariable Long id, @RequestParam boolean status) {
        ColaboradorResponse colaborador = colaboradorService.desligarColaborador(id, status);
        return ResponseEntity.ok(colaborador);
    }

    @GetMapping ("/nome")
    public List<ColaboradorResponse> porNome(@RequestParam String nome) {
        return colaboradorService.porNome(nome);
    }

    @GetMapping ("/ativo")
    public List<ColaboradorResponse> porAtivos() {
        return colaboradorService.colaboradoresAtivos();
    }

    @GetMapping ("/inativo")
    public List<ColaboradorResponse> porInativos() {
        return colaboradorService.colaboradoresInativos();
    }
}
