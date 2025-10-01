package br.unipar.devbackend.microblog.dto;

import br.unipar.devbackend.microblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String conteudo;
    private LocalDateTime createdAt;
    private Long likesCount;
    private AutorDto autor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AutorDto {
        private Long id;
        private String nome;
        private String username;
    }

    public static PostResponseDto fromEntity(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .conteudo(post.getConteudo())
                .createdAt(post.getCreatedAt())
                .likesCount(post.getLikesCount())
                .autor(AutorDto.builder()
                        .id(post.getAutor().getId())
                        .nome(post.getAutor().getNome())
                        .username(post.getAutor().getUsername())
                        .build())
                .build();
    }
}