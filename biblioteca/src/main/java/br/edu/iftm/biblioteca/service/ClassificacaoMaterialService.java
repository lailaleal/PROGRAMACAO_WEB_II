package br.edu.iftm.biblioteca.service;

import java.util.List;
import br.edu.iftm.biblioteca.model.ClassificacaoMaterial;

public interface ClassificacaoMaterialService {

    List<ClassificacaoMaterial> getAllClassificacaoMaterial();

    void saveClassificacaoMaterial(ClassificacaoMaterial material);

    ClassificacaoMaterial getClassificacaoMaterialById(long id);

    void deleteClassificacaoMaterialById(long id);
}