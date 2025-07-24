package com.alura.forumHub.api.dto.usuario;

import com.alura.forumHub.api.domain.usuario.Usuario;

public record UsuarioTopicoDto(String login) {
    public UsuarioTopicoDto(Usuario usuario) {
        this(usuario.getLogin());
    }
}
