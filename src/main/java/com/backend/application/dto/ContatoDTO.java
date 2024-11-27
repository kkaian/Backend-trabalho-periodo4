package com.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {

    private Long id;
    private String tipo;
    private String numero;
    private Long pessoaId;
}
