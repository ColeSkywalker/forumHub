package com.alura.forumHub.api.repository;
import com.alura.forumHub.api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    Usuario findByLogin(String login);

}
