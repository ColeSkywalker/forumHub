ALTER TABLE topicos
    ADD CONSTRAINT uq_topicos_titulo UNIQUE (titulo),
ADD CONSTRAINT uq_topicos_mensagem UNIQUE (mensagem(255));

