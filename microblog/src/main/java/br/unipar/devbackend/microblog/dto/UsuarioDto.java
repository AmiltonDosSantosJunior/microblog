package br.unipar.devbackend.microblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDto {
    @NotBlank
    private String nome;

    @NotBlank
    private String username;
}
