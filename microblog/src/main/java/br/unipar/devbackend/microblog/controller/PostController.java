package br.unipar.devbackend.microblog.controller;

import br.unipar.devbackend.microblog.dto.PostDto;
import br.unipar.devbackend.microblog.model.Post;
import br.unipar.devbackend.microblog.model.Usuario;
import br.unipar.devbackend.microblog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
    public PostController(PostService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Post> criar(@Valid @RequestBody PostDto dto) {
        Post p = Post.builder()
                .conteudo(dto.getConteudo())
                .autor(Usuario.builder().id(dto.getAutorId()).build())
                .build();
        Post criado = service.criar(p);
        return ResponseEntity.created(URI.create("/api/posts/" + criado.getId())).body(criado);
    }

    @GetMapping("/cronologico")
    public ResponseEntity<List<Post>> cronologico() {
        return ResponseEntity.ok(service.listarCronologico());
    }

    @GetMapping("/relevancia")
    public ResponseEntity<List<Post>> relevancia() {
        return ResponseEntity.ok(service.listarRelevancia());
    }

    @PostMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<?> curtir(@PathVariable Long postId, @PathVariable Long usuarioId) {
        service.curtir(postId, usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<?> descurtir(@PathVariable Long postId, @PathVariable Long usuarioId) {
        service.descurtir(postId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
