package com.alura.forumHub.api.dto.topico;

import com.alura.forumHub.api.domain.topico.CursosTopico;
import com.alura.forumHub.api.domain.topico.StatusTopico;
import com.alura.forumHub.api.domain.topico.Topico;

import java.time.LocalDateTime;

public record TopicosListagemDto(Long id, String titulo, String mensagem, LocalDateTime dataCriacao,
                                 StatusTopico statusTopico, CursosTopico cursosTopico, String nomeAutor) {

    public TopicosListagemDto(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),
                 topico.getStatus(), topico.getCurso(), topico.getAutor().getLogin());
    }
}
