package com.alura.forumHub.api.dto.usuario;

import com.alura.forumHub.api.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDto(
        @NotBlank
        @Email
        String login,
        @NotBlank
        String senha){

        public UsuarioLoginDto(Usuario usuario){
                this(usuario.getLogin(), usuario.getSenha());
        }
}
