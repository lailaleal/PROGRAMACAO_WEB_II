package br.edu.iftm.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}