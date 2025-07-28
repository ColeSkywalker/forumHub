package com.alura.forumHub.api.domain.topico;

import com.alura.forumHub.api.domain.usuario.Usuario;
import com.alura.forumHub.api.domain.resposta.Resposta;
import com.alura.forumHub.api.dto.topico.TopicoEditarDto;
import com.alura.forumHub.api.dto.topico.TopicoNovoDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    @Enumerated(EnumType.STRING)
    private StatusTopico status;
    @Enumerated(EnumType.STRING)
    private CursosTopico curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public Topico(TopicoNovoDto dados, Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusTopico.NAO_RESPONDIDO;
        this.curso = dados.curso();
        this.autor = autor;
    }

    public void editarTopico(TopicoEditarDto dados) {
        if (dados.titulo() != null && !dados.titulo().isBlank()) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null && !dados.mensagem().isBlank()) {
            this.mensagem = dados.mensagem();
        }
        if (dados.statusTopico() != null) {
            this.status = dados.statusTopico();
        }
    }

    public void topicoRecebeResposta(){
        this.status =  StatusTopico.RESPONDIDO;
    }

}
