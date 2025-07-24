package com.alura.forumHub.api.dto.topico;

import com.alura.forumHub.api.domain.topico.CursosTopico;
import com.alura.forumHub.api.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoNovoDto (@NotBlank
                             String titulo, @NotBlank String mensagem,
                             @NotNull CursosTopico curso) {
    public TopicoNovoDto(Topico topico) {
        this(topico.getTitulo(), topico.getMensagem(), topico.getCurso());
    }
}
