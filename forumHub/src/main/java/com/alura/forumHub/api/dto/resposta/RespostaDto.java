package com.alura.forumHub.api.dto.resposta;

import com.alura.forumHub.api.domain.resposta.Resposta;

public record RespostaDto(Long id, String mensagem, String autor) {
    public RespostaDto(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getAutor().getLogin());
    }
}

