package br.edu.iftm.biblioteca.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iftm.biblioteca.model.Acervo;
import br.edu.iftm.biblioteca.service.AcervoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/acervo")
public class AcervoRestController {

    @Autowired
    private AcervoService acervoService;

    @GetMapping
    public ResponseEntity<List<Acervo>> getAllAcervo() {
        List<Acervo> acervo = acervoService.listarTodos();
        return ResponseEntity.ok(acervo);
    }

    /**
     * Resposta: 200 OK com o item, ou 404 Not Found se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Acervo> getAcervoById(@PathVariable Long id) {
        try {
            Acervo acervo = acervoService.buscarPorId(id);
            return ResponseEntity.ok(acervo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Corpo da Requisição: JSON do item a ser criado.
     * Resposta: 201 Created com o local do novo recurso e o item criado.
     * 400 Bad Request se a validação falhar.
     */
    @PostMapping
    public ResponseEntity<?> createAcervo(@Valid @RequestBody Acervo acervo, BindingResult result) {
        if (result.hasErrors()) {
            // Retorna 400 Bad Request com os erros de validação
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        
        acervoService.salvar(acervo);

        // Cria a URI para o novo recurso (ex: /api/acervo/5)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(acervo.getId())
                .toUri();

        // Retorna 201 Created
        return ResponseEntity.created(location).body(acervo);
    }

    /**
     * Corpo da Requisição: JSON do item com as atualizações.
     * Resposta: 200 OK com o item atualizado.
     * 404 Not Found se o item não existir.
     * 400 Bad Request se a validação falhar.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAcervo(@PathVariable Long id, @Valid @RequestBody Acervo acervoDetails, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            // Verifica se o item existe
            acervoService.buscarPorId(id);
            acervoDetails.setId(id);
            acervoService.salvar(acervoDetails);
            
            return ResponseEntity.ok(acervoDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Resposta: 204 No Content se a exclusão for bem-sucedida.
     * 404 Not Found se o item não existir.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcervo(@PathVariable Long id) {
        try {           
            acervoService.deletar(id);
            
            // Retorna 204 No Content (sucesso, sem corpo de resposta)
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}