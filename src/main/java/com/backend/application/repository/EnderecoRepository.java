package com.backend.application.repository;

import com.backend.application.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Método para buscar endereços pela cidade
    List<Endereco> findByCidade(String cidade);
}
