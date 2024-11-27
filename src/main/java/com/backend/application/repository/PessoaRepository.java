package com.backend.application.repository;

import com.backend.application.model.Pessoa;
import com.backend.application.model.Role; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    
    Optional<Pessoa> findByEmail(String email);

    
    Optional<Pessoa> findByNome(String nome);

    
    List<Pessoa> findByRole(Role role); 
}
