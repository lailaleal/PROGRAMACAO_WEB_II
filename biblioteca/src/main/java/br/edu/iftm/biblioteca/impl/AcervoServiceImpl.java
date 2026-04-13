package br.edu.iftm.biblioteca.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iftm.biblioteca.model.Acervo;
import br.edu.iftm.biblioteca.repository.AcervoRepository;
import br.edu.iftm.biblioteca.service.AcervoService;

@Service
public class AcervoServiceImpl implements AcervoService {

    private final AcervoRepository acervoRepository;

    public AcervoServiceImpl(AcervoRepository acervoRepository) {
        this.acervoRepository = acervoRepository;
    }

    @Override
    public List<Acervo> listarTodos() {
        return acervoRepository.findAll();
    }

    @Override
    public void salvar(Acervo acervo) {
        acervoRepository.save(acervo);
    }

    @Override
    public Acervo buscarPorId(Long id) {
        return acervoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acervo não encontrado: " + id));
    }

    @Override
    public void deletar(Long id) {
        acervoRepository.deleteById(id);
    }
}