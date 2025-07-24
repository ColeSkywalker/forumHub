package com.alura.forumHub.api.dto.topico;
import com.alura.forumHub.api.domain.topico.CursosTopico;
import com.alura.forumHub.api.domain.topico.StatusTopico;
import com.alura.forumHub.api.domain.topico.Topico;
import com.alura.forumHub.api.dto.resposta.RespostaDto;

import java.time.LocalDateTime;
import java.util.List;


public record TopicoDetalhadoDto(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico statusTopico,
        CursosTopico cursosTopico,
        List<RespostaDto> respostas,
        String nomeAutorTopico
) {
    public TopicoDetalhadoDto(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getCurso(),
                topico.getRespostas().stream().map(RespostaDto::new).toList(),
                topico.getAutor().getLogin()
        );
    }
}


