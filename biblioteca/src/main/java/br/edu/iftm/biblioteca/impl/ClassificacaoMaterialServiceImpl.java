package br.edu.iftm.biblioteca.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.biblioteca.model.ClassificacaoMaterial;
import br.edu.iftm.biblioteca.repository.ClassificacaoMaterialRepository;
import br.edu.iftm.biblioteca.service.ClassificacaoMaterialService;

@Service
public class ClassificacaoMaterialServiceImpl implements ClassificacaoMaterialService {

    @Autowired
    private ClassificacaoMaterialRepository classificacaoMaterialRepository;

    @Override
    public List<ClassificacaoMaterial> getAllClassificacaoMaterial() {
        return classificacaoMaterialRepository.findAll();
    }

    @Override
    public void saveClassificacaoMaterial(ClassificacaoMaterial material) {
        this.classificacaoMaterialRepository.save(material);
    }

    @Override
    public ClassificacaoMaterial getClassificacaoMaterialById(long id) {
        Optional<ClassificacaoMaterial> optional = classificacaoMaterialRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Material não encontrado com o id: " + id);
        }
    }

    @Override
    public void deleteClassificacaoMaterialById(long id) {
        this.classificacaoMaterialRepository.deleteById(id);
    }
}