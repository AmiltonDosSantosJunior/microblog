package br.unipar.devbackend.microblog.service;

import br.unipar.devbackend.microblog.exception.BadRequestException;
import br.unipar.devbackend.microblog.exception.ResourceNotFoundException;
import br.unipar.devbackend.microblog.model.Curtida;
import br.unipar.devbackend.microblog.model.Post;
import br.unipar.devbackend.microblog.model.Usuario;
import br.unipar.devbackend.microblog.repository.CurtidaRepository;
import br.unipar.devbackend.microblog.repository.PostRepository;
import br.unipar.devbackend.microblog.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepo;
    private final UsuarioRepository usuarioRepo;
    private final CurtidaRepository curtidaRepo;

    public PostService(PostRepository postRepo, UsuarioRepository usuarioRepo, CurtidaRepository curtidaRepo) {
        this.postRepo = postRepo;
        this.usuarioRepo = usuarioRepo;
        this.curtidaRepo = curtidaRepo;
    }

    @Transactional
    public Post criar(Post post) {
        Usuario autor = usuarioRepo.findById(post.getAutor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));
        post.setAutor(autor);
        return postRepo.save(post);
    }

    public List<Post> listarCronologico() { return postRepo.findAllByOrderByCreatedAtDesc(); }

    public List<Post> listarRelevancia() { return postRepo.findAllByOrderByLikesCountDesc(); }

    @Transactional
    public void curtir(Long postId, Long usuarioId) {
        postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));
        usuarioRepo.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        var existente = curtidaRepo.findByPostIdAndUsuarioId(postId, usuarioId);
        if (existente.isPresent()) throw new BadRequestException("Usuário já curtiu este post");

        Post post = postRepo.getReferenceById(postId);
        Usuario usuario = usuarioRepo.getReferenceById(usuarioId);

        Curtida curtida = Curtida.builder().post(post).usuario(usuario).build();
        curtidaRepo.save(curtida);

        post.setLikesCount(post.getLikesCount() + 1);
        postRepo.save(post);
    }

    @Transactional
    public void descurtir(Long postId, Long usuarioId) {
        var existente = curtidaRepo.findByPostIdAndUsuarioId(postId, usuarioId)
                .orElseThrow(() -> new BadRequestException("Curtida não encontrada"));
        curtidaRepo.delete(existente);

        Post post = postRepo.getReferenceById(postId);
        post.setLikesCount(Math.max(0L, post.getLikesCount() - 1));
        postRepo.save(post);
    }
}
