package com.alura.forumHub.api.controller;

import com.alura.forumHub.api.domain.usuario.Usuario;
import com.alura.forumHub.api.dto.usuario.UsuarioLoginDto;
import com.alura.forumHub.api.infra.security.TokenDto;
import com.alura.forumHub.api.infra.security.TokenJWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenJWTService tokenJWTService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid UsuarioLoginDto dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenJWTService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDto(tokenJWT));
    }

}

