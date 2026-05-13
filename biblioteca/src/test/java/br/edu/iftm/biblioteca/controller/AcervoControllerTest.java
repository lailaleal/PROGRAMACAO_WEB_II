package br.edu.iftm.biblioteca.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iftm.biblioteca.config.TestConfig;
import br.edu.iftm.biblioteca.model.Acervo;
import br.edu.iftm.biblioteca.service.AcervoService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
public class AcervoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AcervoService acervoService;

    @AfterEach
    void resetMocks() {
        reset(acervoService);
    }

    private List<Acervo> testCreateAcervoList() {
        Acervo acervo = new Acervo();
        acervo.setId(1L);
        acervo.setTitulo("Livro Teste");
        acervo.setAutor("Autor Teste");
        acervo.setTipo("Livro");
        acervo.setAno(2024);
        acervo.setQuantidade(10);

        return List.of(acervo);
    }

    @Test
    @DisplayName("GET /acervo - Listar acervo na tela sem usuário autenticado")
    void testListNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/acervo"))
            .andExpect(status().is3xxRedirection());  // Redireciona para login
    }

    @Test
    @WithMockUser
    @DisplayName("GET /acervo - Listar acervo na tela com usuário logado")
    void testListAuthenticatedUser() throws Exception {
        when(acervoService.listarTodos()).thenReturn(testCreateAcervoList());

        mockMvc.perform(get("/acervo"))
               .andExpect(status().isOk())
               .andExpect(view().name("product/index"))
               .andExpect(model().attributeExists("listaAcervo"))
               .andExpect(content().string(containsString("Listagem do Acervo")));
    }

    @Test
    @WithMockUser(username = "admin@biblioteca.com", authorities = { "Admin" })
    @DisplayName("GET /acervo/create - Exibe formulário de criação para Admin")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/acervo/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/create"))
                .andExpect(model().attributeExists("acervo"))
                .andExpect(content().string(containsString("Novo Acervo")));
    }

    @Test
    @WithMockUser(username = "user@biblioteca.com", authorities = { "User" })
    @DisplayName("GET /acervo/create - Usuário não admin NÃO pode acessar o formulário")
    void testCreateFormNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/acervo/create"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /acervo/save - Falha na validação e retorna para o formulário")
    void testSaveAcervoValidationError() throws Exception {
        Acervo acervo = new Acervo();

        mockMvc.perform(post("/acervo/save")
                        .with(csrf())
                        .flashAttr("acervo", acervo))
                .andExpect(status().isOk())
                .andExpect(view().name("product/create"))
                .andExpect(model().attributeHasErrors("acervo"));

        verify(acervoService, never()).salvar(any(Acervo.class));
    }

    @Test
    @WithMockUser(username = "admin@biblioteca.com", authorities = { "Admin" })
    @DisplayName("POST /acervo/save - Acervo válido é salvo com sucesso")
    void testSaveValidAcervo() throws Exception {
        Acervo acervo = new Acervo();
        acervo.setTitulo("Novo Livro");
        acervo.setAutor("Novo Autor");
        acervo.setTipo("Livro");
        acervo.setAno(2024);
        acervo.setQuantidade(5);

        mockMvc.perform(post("/acervo/save")
                        .with(csrf())
                        .flashAttr("acervo", acervo))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/acervo"));

        verify(acervoService).salvar(any(Acervo.class));
    }
}