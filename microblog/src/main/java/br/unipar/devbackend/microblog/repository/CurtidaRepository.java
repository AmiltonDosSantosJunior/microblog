package br.unipar.devbackend.microblog.repository;

import br.unipar.devbackend.microblog.model.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    Optional<Curtida> findByPostIdAndUsuarioId(Long postId, Long usuarioId);
}
