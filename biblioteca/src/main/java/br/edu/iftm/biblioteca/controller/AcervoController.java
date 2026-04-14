package br.edu.iftm.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.edu.iftm.biblioteca.model.Acervo;
import br.edu.iftm.biblioteca.service.AcervoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/acervo") //
public class AcervoController {

    private final AcervoService service;

    // ✔ Injeção via construtor (melhor prática)
    public AcervoController(AcervoService service) {
        this.service = service;
    }

    // 🔹 LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaAcervo", service.listarTodos());
        return "index";
    }

    // 🔹 ABRIR FORMULÁRIO
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("acervo", new Acervo());
        return "form";
    }

    // 🔹 SALVAR (com validação)
    @PostMapping("/save")
    public String save(@ModelAttribute("acervo") @Valid Acervo acervo,
                       BindingResult result,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("acervo", acervo);
            return "form";
        }

        service.salvar(acervo);
        return "redirect:/acervo";
    }

    // 🔹 EDITAR
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("acervo", service.buscarPorId(id));
        return "form";
    }

    // 🔹 DELETAR
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/acervo";
    }
}