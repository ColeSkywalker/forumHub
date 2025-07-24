package com.alura.forumHub.api.controller;

import com.alura.forumHub.api.domain.topico.Topico;
import com.alura.forumHub.api.domain.usuario.Usuario;
import com.alura.forumHub.api.dto.topico.TopicoDetalhadoDto;
import com.alura.forumHub.api.dto.topico.TopicoEditarDto;
import com.alura.forumHub.api.dto.topico.TopicoNovoDto;
import com.alura.forumHub.api.dto.topico.TopicosListagemDto;
import com.alura.forumHub.api.repository.TopicoRepository;
import com.alura.forumHub.api.repository.UsuarioRepository;
import com.alura.forumHub.api.service.TopicoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity publicarTopico(@RequestBody @Valid TopicoNovoDto dadosTopico, UriComponentsBuilder uriBuilder){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        Usuario autor = usuarioRepository.findByLogin(login);

        Topico topico = new Topico(dadosTopico, autor);
        repository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDetalhadoDto(topico));
    }
    @GetMapping
    public ResponseEntity<Page<TopicosListagemDto>> listarTodosTopicos(@PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.DESC) Pageable paginacao){
        var page = repository.findAll(paginacao).map(TopicosListagemDto::new);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long id){
        var topico = repository.getTopicosById(id);
        return ResponseEntity.ok(new TopicoDetalhadoDto(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity editarTopico(@RequestBody @Valid TopicoEditarDto dados, @PathVariable Long id){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var topico = repository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        topicoService.verificarAutor(topico.getAutor(), usuarioLogado);

        topico.editarTopico(dados);
        return ResponseEntity.ok(new TopicoDetalhadoDto(topico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarTopico(@PathVariable Long id){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        topicoService.verificarAutor(topico.getAutor(), usuarioLogado);

        repository.delete(topico);
        return ResponseEntity.noContent().build();
    }



}
