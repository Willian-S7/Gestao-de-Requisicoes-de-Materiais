package com.wil.RequisicoesMateriais.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.RequisicoesMateriais.dto.request.MaterialRequest;
import com.wil.RequisicoesMateriais.dto.response.MaterialResponse;
import com.wil.RequisicoesMateriais.model.Material;
import com.wil.RequisicoesMateriais.repository.MaterialRepository;

@Service 
public class MaterialService {

    private final MaterialRepository materialRepository;
    
    public MaterialService (MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public MaterialResponse toDTO(Material material) {
        MaterialResponse materialDTO = new MaterialResponse(material.getId(), material.getNome(), material.getDescricao(), material.getPrecoUni(), material.getQuantidadeEstoque(), material.getEstoqueMinimo(), material.isAtivo());

        return materialDTO;
    }

    public MaterialResponse criarNovoMaterial(MaterialRequest request) {
        Material material = new Material();
        material.setNome(request.nome());
        material.setDescricao(request.descricao());
        material.setPrecoUni(request.precoUni());
        material.setQuantidadeEstoque(request.quantidadeEstoque());
        material.setEstoqueMinimo(request.estoqueMinimo());
        material.setAtivo(true);
        materialRepository.save(material);
        return toDTO(material);
    }

    public List<MaterialResponse> listarMateriais() {
        List<MaterialResponse> materiais = materialRepository.findAll().stream().map(material -> toDTO(material)).toList();
        return materiais;
    }

    public Material buscarMaterialporId(Long id) {
        return materialRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material não encontrado"));
    }

    public MaterialResponse buscarMaterialDTO (Long id) {
            Material material = buscarMaterialporId(id);
         return toDTO(material);
    }

    public MaterialResponse alterarMaterial(Long id, MaterialRequest request) {
        Material material = buscarMaterialporId(id);
        material.setNome(request.nome());
        material.setDescricao(request.descricao());
        material.setPrecoUni(request.precoUni());
        material.setQuantidadeEstoque(request.quantidadeEstoque());
        material.setEstoqueMinimo(request.estoqueMinimo());
        materialRepository.save(material);
        return toDTO(material);
    }

    public MaterialResponse alterarStatus(Long id, boolean ativo) {
        Material materialEdit = buscarMaterialporId(id);

        if (materialEdit.isAtivo() == ativo) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status igual ao atual");
        }

        materialEdit.setAtivo(ativo);

        return toDTO(materialEdit);
    }

    public void deleteMaterial(Long id) {
        Material materialDelete = buscarMaterialporId(id);
        materialRepository.delete(materialDelete);
    }
    
    public List<MaterialResponse> porNome(String nome){
        return materialRepository.findByNome(nome)
        .stream()
        .map(material -> toDTO(material))
        .toList();
    } 
    
        public List<MaterialResponse> porAtivoTrue(){
        return materialRepository.findByAtivoTrue()
        .stream()
        .map(material -> toDTO(material))
        .toList();
    }

    public List<MaterialResponse> porAtivoFalse(){
        return materialRepository.findByAtivoFalse()
        .stream()
        .map(material -> toDTO(material))
        .toList();
    }
    
    public List<MaterialResponse> porEstoqueBaixo() {
        return materialRepository.findMateriaisComEstoqueBaixo()
        .stream()
        .map(mat -> toDTO(mat))
        .toList();
    }
}
