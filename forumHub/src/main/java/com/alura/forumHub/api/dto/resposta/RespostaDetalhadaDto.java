package com.alura.forumHub.api.dto.resposta;

import com.alura.forumHub.api.domain.resposta.Resposta;
import com.alura.forumHub.api.domain.topico.Topico;
import com.alura.forumHub.api.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record RespostaDetalhadaDto(String mensagem, LocalDateTime dataCriacao,  String autorResposta, String tituloTopico, String conteudoTopico, String autorTopico) {
    public RespostaDetalhadaDto(Resposta resposta, Usuario autor, Topico topico) {
        this(resposta.getMensagem(),resposta.getDataCriacao(), resposta.getAutor().getLogin(), topico.getTitulo(), topico.getMensagem(), autor.getLogin());
    }

    public RespostaDetalhadaDto(Resposta resposta, Topico topico) {
        this(resposta.getMensagem(), resposta.getDataCriacao(), resposta.getAutor().getLogin(), topico.getTitulo(), topico.getMensagem(), topico.getAutor().getLogin());
    }
}
