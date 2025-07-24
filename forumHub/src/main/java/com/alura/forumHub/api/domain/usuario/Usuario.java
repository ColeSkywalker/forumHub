package com.alura.forumHub.api.domain.usuario;

import com.alura.forumHub.api.domain.resposta.Resposta;
import com.alura.forumHub.api.domain.topico.Topico;
import com.alura.forumHub.api.dto.usuario.UsuarioLoginDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;

    private String senha;


    @OneToMany(mappedBy = "autor")
    private List<Topico> topicosCriados;
    @OneToMany(mappedBy = "autor")
    private List<Resposta> respostasDoUsuario;

    public Usuario(UsuarioLoginDto dados) {
        login = dados.login();
        senha = dados.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setSenha(String senhaCriptografada) {
        this.senha = senhaCriptografada;
    }
}
