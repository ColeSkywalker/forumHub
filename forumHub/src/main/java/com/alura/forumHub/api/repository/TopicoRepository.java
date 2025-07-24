package com.alura.forumHub.api.repository;


import com.alura.forumHub.api.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAll(Pageable pageable);

    Topico getTopicosById(Long id);

    @Override
    void deleteById(Long id);
}
