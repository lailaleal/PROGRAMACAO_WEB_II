package br.edu.iftm.biblioteca.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import br.edu.iftm.biblioteca.service.AcervoService;
import org.mockito.Mockito;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary  // ← ADICIONE ESTA LINHA
    public AcervoService acervoService() {
        return Mockito.mock(AcervoService.class);
    }
}