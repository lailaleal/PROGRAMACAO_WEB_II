package br.edu.iftm.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.edu.iftm.biblioteca.model.Acervo;
import br.edu.iftm.biblioteca.service.AcervoService;

@Controller
public class AcervoController {

    private final AcervoService service;

    public AcervoController(AcervoService service) {
        this.service = service;
    }

    // 🔹 LISTAR
    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("listaAcervo", service.listarTodos());
        return "index";
    }

    // 🔹 ABRIR FORMULÁRIO
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("acervo", new Acervo());
        return "form";
    }

    // 🔹 SALVAR
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("acervo") Acervo acervo) {
        service.salvar(acervo);
        return "redirect:/";
    }

    // 🔹 EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("acervo", service.buscarPorId(id));
        return "form";
    }

    // 🔹 DELETAR
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/";
    }
}