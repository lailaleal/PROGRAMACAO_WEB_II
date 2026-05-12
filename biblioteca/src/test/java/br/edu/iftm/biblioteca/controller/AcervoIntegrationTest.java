package br.edu.iftm.biblioteca.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iftm.biblioteca.model.Acervo;
import br.edu.iftm.biblioteca.repository.AcervoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class AcervoIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AcervoRepository acervoRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSaveAcervoIntegration() throws Exception {

        Acervo acervo = new Acervo();
        acervo.setTitulo("Livro Integração Teste");
        acervo.setAutor("Autor Teste");
        acervo.setTipo("Livro");
        acervo.setAno(2024);
        acervo.setQuantidade(10);

        mockMvc.perform(post("/acervo/save")
                .with(csrf())
                .flashAttr("acervo", acervo))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/acervo"));

        // Verifica no banco se foi salvo
        assertTrue(acervoRepository.findAll()
                .stream()
                .anyMatch(a -> "Livro Integração Teste".equals(a.getTitulo())));
    }
}
