ALTER TABLE respostas
    ADD CONSTRAINT chk_resposta_mensagem_length
        CHECK (CHAR_LENGTH(mensagem) BETWEEN 10 AND 3000);