package com.alura.forumHub.api.service;

import com.alura.forumHub.api.domain.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    public void verificarAutor(Usuario autorPost, Usuario usuarioLogado) {
        if (!autorPost.getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Acesso negado.");
        }
    }

}
