package com.alura.forumHub.api.controller;

import com.alura.forumHub.api.service.AutenticacaoService;
import com.alura.forumHub.api.domain.usuario.Usuario;
import com.alura.forumHub.api.dto.usuario.UsuarioLoginDto;
import com.alura.forumHub.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioLoginDto dados, UriComponentsBuilder uriBuilder){
        var usuario = new Usuario(dados);
        autenticacaoService.encriptografarSenha(usuario);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioLoginDto(usuario));
    }
}
