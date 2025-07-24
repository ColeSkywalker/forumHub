package com.alura.forumHub.api.dto.resposta;

import jakarta.validation.constraints.NotBlank;


public record RespostaCriarDto(@NotBlank String mensagem) {
}
