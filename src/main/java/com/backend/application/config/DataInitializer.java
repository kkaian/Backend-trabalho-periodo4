package com.backend.application.config;

import com.backend.application.model.Pessoa;
import com.backend.application.model.Role;
import com.backend.application.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (pessoaRepository.findByEmail("admin@example.com").isEmpty()) {
                Pessoa admin = new Pessoa();
                admin.setNome("Admin");
                admin.setEmail("admin@example.com");
                admin.setSenha(passwordEncoder.encode("admin123")); // Criptografa a senha
                admin.setRole(Role.ADMIN);

                pessoaRepository.save(admin);
                System.out.println("Usuário ADMIN criado com sucesso!");
            }
        };
    }
}
