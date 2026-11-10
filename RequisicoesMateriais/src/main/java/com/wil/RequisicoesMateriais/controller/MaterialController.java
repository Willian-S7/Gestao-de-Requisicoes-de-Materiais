package com.wil.RequisicoesMateriais.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wil.RequisicoesMateriais.dto.request.MaterialRequest;
import com.wil.RequisicoesMateriais.dto.response.MaterialResponse;
import com.wil.RequisicoesMateriais.service.MaterialService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/materiais")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController (MaterialService materialService){

        this.materialService = materialService;
    }

    @PostMapping 
    public ResponseEntity<MaterialResponse> novoMaterial (@RequestBody  @Valid MaterialRequest request) {
        MaterialResponse material = materialService.criarNovoMaterial(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(material);
    }

    @GetMapping 
    public ResponseEntity<List<MaterialResponse>> listarMateriais (){
        List<MaterialResponse> materiais = materialService.listarMateriais();
        return ResponseEntity.ok(materiais);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<MaterialResponse> buscarMaterial(@PathVariable Long id) {
        MaterialResponse material = materialService.buscarMaterialDTO(id);
        return ResponseEntity.ok(material);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<MaterialResponse> alterarMaterial(@PathVariable Long id, @RequestBody @Valid MaterialRequest request) {
        MaterialResponse material = materialService.alterarMaterial(id, request);
        return ResponseEntity.ok(material);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deletarMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<MaterialResponse> alterarStatus (@PathVariable Long id, @RequestParam boolean ativo) {
        MaterialResponse material = materialService.alterarStatus(id, ativo);
        return ResponseEntity.ok(material);
    }
}
