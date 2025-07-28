package com.alura.forumHub.api.controller;
import com.alura.forumHub.api.dto.topico.TopicoEditarDto;
import com.alura.forumHub.api.dto.topico.TopicoNovoDto;
import com.alura.forumHub.api.dto.topico.TopicosListagemDto;
import com.alura.forumHub.api.repository.TopicoRepository;
import com.alura.forumHub.api.repository.UsuarioRepository;
import com.alura.forumHub.api.service.TopicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<TopicoNovoDto> dadosTopicoJson;
    @Autowired
    private JacksonTester<TopicosListagemDto> dadosDetalhamentoTopicoJson;
    @Autowired
    private JacksonTester<TopicoEditarDto> dadosTopicoEditarJson;
    @MockitoBean
    private TopicoService topicoService;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    @DisplayName("Deveria retornar 400 quando informações erradas")
    @WithMockUser
    void publicarTopico_cenario1() throws Exception {
        var response = mockMvc.perform(post("/topicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar 404 quando tópico não existir")
    @WithMockUser
    void detalharTopico() throws Exception {
        Long idInexistente = 9999L;
        var response = mockMvc.perform(get("/topicos/{id}", idInexistente))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}