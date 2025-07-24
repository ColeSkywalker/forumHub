CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          login VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL
);

CREATE TABLE topicos (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         titulo VARCHAR(255) NOT NULL,
                         mensagem TEXT NOT NULL,
                         data_criacao DATETIME,
                         status VARCHAR(50),
                         curso VARCHAR(50),
                         autor_id BIGINT,
                         CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

CREATE TABLE respostas (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           mensagem TEXT NOT NULL,
                           data_criacao DATETIME,
                           topico_id BIGINT,
                           autor_id BIGINT,
                           CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
                           CONSTRAINT fk_resposta_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
