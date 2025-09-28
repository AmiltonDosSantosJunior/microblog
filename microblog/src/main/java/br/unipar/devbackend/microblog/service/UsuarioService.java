package br.unipar.devbackend.microblog.service;

import br.unipar.devbackend.microblog.exception.BadRequestException;
import br.unipar.devbackend.microblog.model.Usuario;
import br.unipar.devbackend.microblog.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    @Transactional
    public Usuario criar(Usuario usuario) {
        repo.findByUsername(usuario.getUsername())
                .ifPresent(u -> { throw new BadRequestException("username já existe"); });
        return repo.save(usuario);
    }

    @Transactional
    public Usuario editar(Usuario usuario) {
        if (usuario.getId() == null) throw new BadRequestException("id é obrigatório para editar");
        Usuario existente = repo.findById(usuario.getId())
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado"));
        existente.setNome(usuario.getNome());
        // não altera username aqui (ou permitir com checagem)
        return repo.save(existente);
    }

    public List<Usuario> listar() { return repo.findAll(); }

    public Usuario findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new BadRequestException("Usuário não encontrado"));
    }
}
