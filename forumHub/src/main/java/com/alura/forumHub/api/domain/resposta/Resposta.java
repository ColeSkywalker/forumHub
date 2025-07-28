package com.alura.forumHub.api.domain.resposta;
import com.alura.forumHub.api.domain.topico.Topico;
import com.alura.forumHub.api.domain.usuario.Usuario;
import com.alura.forumHub.api.dto.resposta.RespostaAlterarDto;
import com.alura.forumHub.api.dto.resposta.RespostaCriarDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    @Setter
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    @Setter
    private Usuario autor;

    public Resposta(RespostaCriarDto dados, Usuario autor, Topico topico) {
        this.mensagem = dados.mensagem();
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();
        this.autor = autor;
    }

    public void alterarResposta(RespostaAlterarDto dados) {
        if(dados.mensagem() != null){
            this.mensagem = dados.mensagem();
        }
    }
}
