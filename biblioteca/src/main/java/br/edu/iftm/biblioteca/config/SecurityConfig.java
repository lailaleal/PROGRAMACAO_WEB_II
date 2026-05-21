package br.edu.iftm.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService uds;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Cadeia de segurança para a API REST (ordem 1 - verifica primeiro)
     * - Usa Basic Authentication
     * - CSRF desabilitado
     * - Endpoints /api/** são protegidos
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .httpBasic(basic -> {})
            .authenticationProvider(authenticationProvider(uds, encoder));

        return http.build();
    }

    /**
     * Cadeia de segurança para a interface web (ordem 2 - padrão)
     * - Usa formulário de login
     * - CSRF habilitado
     * - Protege páginas web
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                // Páginas públicas (acesso liberado)
                .requestMatchers("/home", "/register", "/saveUser", "/login", "/error", 
                                 "/css/**", "/imagens/**", 
                                 "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs*/**").permitAll()
                // Acervo exige autenticação
                .requestMatchers("/acervo").authenticated()
                // Operações de escrita exigem role ADMIN
                .requestMatchers("/acervo/create", "/acervo/edit/**", "/acervo/delete/**").hasAuthority("Admin")
                // Qualquer outra requisição exige autenticação
                .anyRequest().authenticated()
            )
            // Configuração do formulário de login
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/acervo", true)
                .permitAll()
            )
            // Configuração do logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // Página de acesso negado
            .exceptionHandling(handling -> handling
                .accessDeniedPage("/accessDenied")
            )
            .authenticationProvider(authenticationProvider(uds, encoder));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}