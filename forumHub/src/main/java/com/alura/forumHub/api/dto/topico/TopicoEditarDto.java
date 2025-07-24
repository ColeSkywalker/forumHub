package com.alura.forumHub.api.dto.topico;

import com.alura.forumHub.api.domain.topico.StatusTopico;
import com.alura.forumHub.api.domain.topico.Topico;

public record TopicoEditarDto(Long id, String titulo, String mensagem, StatusTopico statusTopico) {
    public TopicoEditarDto(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getStatus());
    }
}
