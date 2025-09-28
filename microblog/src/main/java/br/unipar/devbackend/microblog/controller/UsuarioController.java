package br.unipar.devbackend.microblog.controller;

import br.unipar.devbackend.microblog.dto.UsuarioDto;
import br.unipar.devbackend.microblog.model.Usuario;
import br.unipar.devbackend.microblog.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody UsuarioDto dto) {
        Usuario u = Usuario.builder().nome(dto.getNome()).username(dto.getUsername()).build();
        Usuario criado = service.criar(u);
        return ResponseEntity.created(URI.create("/api/usuarios/" + criado.getId())).body(criado);
    }

    @PutMapping
    public ResponseEntity<Usuario> editar(@Valid @RequestBody Usuario usuario) {
        Usuario atualizado = service.editar(usuario);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
