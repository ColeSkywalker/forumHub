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
import jakarta.persistence.EntityNotFoundException;
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

    @PostMapping
    @Transactional
    public ResponseEntity publicarResposta(@RequestBody @Valid RespostaCriarDto dados, UriComponentsBuilder uriBuilder, @PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        Usuario autor = usuarioRepository.findByLogin(login);
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        var resposta = new Resposta(dados, autor, topico);
        topico.topicoRecebeResposta();

        repository.save(resposta);
        var uri = uriBuilder.path("respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new RespostaDetalhadaDto(resposta, autor, topico));
    }

    @GetMapping("/{id2}")
    public ResponseEntity detalharResposta(@PathVariable Long id, @PathVariable Long id2){
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        Resposta resposta = repository.findById(id2).orElseThrow(() -> new RuntimeException("Resposta não encontrada"));

        return ResponseEntity.ok(new RespostaDetalhadaDto(resposta, topico));
    }

    @GetMapping
    public ResponseEntity listarTodasRespostasDoTopico(@PathVariable Long id){
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));


        return ResponseEntity.ok(new RespostasDoTopicoDto(topico));
    }

    @PutMapping("{id2}")
    @Transactional
    public ResponseEntity editarResposta(@RequestBody @Valid RespostaAlterarDto dados, @PathVariable Long id, @PathVariable Long id2){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        Resposta resposta = repository.findById(id2).orElseThrow(() -> new RuntimeException("Resposta não encontrada"));

        respostaService.verificarAutor(resposta.getAutor(), usuarioLogado);

        resposta.alterarResposta(dados);

        return ResponseEntity.ok(new RespostaDetalhadaDto(resposta, topico));
    }

    @DeleteMapping("{id2}")
    @Transactional
    public ResponseEntity deletarResposta(@PathVariable Long id2){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var resposta = repository.findById(id2)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada"));
        respostaService.verificarAutor(resposta.getAutor(), usuarioLogado);


        repository.delete(resposta);
        return ResponseEntity.noContent().build();
    }
}
