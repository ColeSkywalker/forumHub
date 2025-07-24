package com.alura.forumHub.api.dto.resposta;

import com.alura.forumHub.api.domain.resposta.Resposta;
import com.alura.forumHub.api.domain.topico.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record RespostasDoTopicoDto(String tituloTopico, String conteudoTopico, LocalDateTime dataCriacaoTopico, String autorTopico, List<RespostaDto> respostas) {

    public RespostasDoTopicoDto(Topico topico) {
        this(topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getAutor().getLogin(), topico.getRespostas().stream().map(RespostaDto::new).toList());
    }
}
