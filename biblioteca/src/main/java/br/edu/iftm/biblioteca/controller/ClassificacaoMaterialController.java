package br.edu.iftm.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.biblioteca.model.ClassificacaoMaterial;
import br.edu.iftm.biblioteca.service.ClassificacaoMaterialService;

@Controller
public class ClassificacaoMaterialController {

    @Autowired
    private ClassificacaoMaterialService classificacaoMaterialService;

    @GetMapping("/classificacaoMaterial")
    public String index(Model model) {
        model.addAttribute("listaMateriais", classificacaoMaterialService.getAllClassificacaoMaterial());
        return "classificacaoMaterial/index";
    }

    @GetMapping("/classificacaoMaterial/create")
    public String create(Model model) {
        model.addAttribute("material", new ClassificacaoMaterial());
        return "classificacaoMaterial/create";
    }

    @PostMapping("/classificacaoMaterial/save")
    public String save(@ModelAttribute("material") ClassificacaoMaterial material) {
        classificacaoMaterialService.saveClassificacaoMaterial(material);
        return "redirect:/classificacaoMaterial";
    }
}
