package com.alura.forumHub.api.repository;

import com.alura.forumHub.api.domain.resposta.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository <Resposta, Long> {

}
