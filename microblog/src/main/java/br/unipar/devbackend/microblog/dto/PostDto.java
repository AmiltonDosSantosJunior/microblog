package br.unipar.devbackend.microblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostDto {
    @NotBlank
    private String conteudo;

    @NotNull
    private Long autorId;
}
