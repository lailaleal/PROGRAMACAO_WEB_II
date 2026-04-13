package br.edu.iftm.biblioteca.service;

import java.util.List;
import br.edu.iftm.biblioteca.model.Acervo;

public interface AcervoService {

    List<Acervo> listarTodos();

    void salvar(Acervo acervo);

    Acervo buscarPorId(Long id);

    void deletar(Long id);
}