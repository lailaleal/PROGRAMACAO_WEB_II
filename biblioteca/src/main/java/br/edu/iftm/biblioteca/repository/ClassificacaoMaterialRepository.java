package br.edu.iftm.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.biblioteca.model.ClassificacaoMaterial;

@Repository
public interface ClassificacaoMaterialRepository extends JpaRepository<ClassificacaoMaterial, Long> {

}