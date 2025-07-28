package com.alura.forumHub.api.controller;

import com.alura.forumHub.api.domain.resposta.Resposta;
import com.alura.forumHub.api.domain.topico.Topico;
import com.alura.forumHub.api.domain.usuario.Usuario;
import com.alura.forumHub.api.dto.resposta.RespostaAlterarDto;
import com.alura.forumHub.api.dto.resposta.RespostaDetalhadaDto;
import com.alura.forumHub.api.dto.resposta.RespostaCriarDto;
import com.alura.forumHub.api.dto.resposta.RespostasDoTopicoDto;
import com.alura.forumHub.api.repository.RespostaRepository;
import com.alura.forumHub.api.repository.TopicoRepository;
import com.alura.forumHub.api.repository.UsuarioRepository;
import com.alura.forumHub.api.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos/{id}/respostas")
public class RespostaController {
    @Autowired
    private RespostaRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private RespostaService respostaService;
    @Autowired
    private RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity publicarResposta(@RequestBody @Valid RespostaCriarDto dados, UriComponentsBuilder uriBuilder, @PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        Usuario autor = usuarioRepository.findByLogin(login);

        return topicoRepository.findById(id).map(topico -> {
            var resposta = new Resposta(dados, autor, topico);
            topico.topicoRecebeResposta();
            repository.save(resposta);
            var uri = uriBuilder.path("respostas/{id}").buildAndExpand(resposta.getId()).toUri();
            return ResponseEntity.created(uri).body(new RespostaDetalhadaDto(resposta, autor, topico));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id2}")
    public ResponseEntity detalharResposta(@PathVariable Long id, @PathVariable Long id2){
        return topicoRepository.findById(id).map(topico -> {
            return repository.findById(id2)
                    .map(resposta -> ResponseEntity.ok(new RespostaDetalhadaDto(resposta, topico)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity listarTodasRespostasDoTopico(@PathVariable Long id){
        return topicoRepository.findById(id).map(topico -> ResponseEntity.ok(new RespostasDoTopicoDto(topico)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id2}")
    @Transactional
    public ResponseEntity editarResposta(@RequestBody @Valid RespostaAlterarDto dados, @PathVariable Long id, @PathVariable Long id2){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return topicoRepository.findById(id).map(topico -> {
            return repository.findById(id2)
                    .map(resposta -> {
                        respostaService.verificarAutor(resposta.getAutor(), usuarioLogado);
                        resposta.alterarResposta(dados);
                        return ResponseEntity.ok(new RespostaDetalhadaDto(resposta, topico));
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id2}")
    @Transactional
    public ResponseEntity deletarResposta(@PathVariable Long id2){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findById(id2)
                .map(resposta -> {
                    respostaService.verificarAutor(resposta.getAutor(), usuarioLogado);
                    repository.delete(resposta);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
