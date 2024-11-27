package com.backend.application.dto;

import com.backend.application.model.Role; // Importando Role
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;
    private String nome;
    private String email;
    private Role role; // Usando o enum Role

    // Pode adicionar a senha apenas quando necessário (por exemplo, para criação de pessoa)
    private String senha;

    // Construtor adicional para o caso de passar Role (caso precise)
    public PessoaDTO(Long id, String nome, String email, Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }
}
