package br.edu.iftm.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ClassificacaoMaterialController {

    @GetMapping("/product")
    public String getMethodName() {
        return "/product/index";
    }
    
}
