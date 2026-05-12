package br.edu.iftm.biblioteca.config;

import br.edu.iftm.biblioteca.service.AcervoService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public AcervoService acervoService() {
        return Mockito.mock(AcervoService.class);
    }

}