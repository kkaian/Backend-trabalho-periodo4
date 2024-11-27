package com.backend.application.repository;

import com.backend.application.model.Pessoa;
import com.backend.application.model.Role; // Importando a enum Role
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // Buscar uma pessoa pelo email (útil para login)
    Optional<Pessoa> findByEmail(String email);

    // Caso precise buscar por nome (opcional)
    Optional<Pessoa> findByNome(String nome);

    // Buscar pessoas pelo role
    List<Pessoa> findByRole(Role role); // Novo método para buscar por role
}
