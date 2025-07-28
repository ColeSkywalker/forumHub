# ForumHub API REST

API REST construída com Spring Boot para gerenciamento de tópicos, respostas e usuários em um fórum. Suporta autenticação com JWT e segue boas práticas RESTful. Desenvolvida no Bootcamp ONE ORACLE + Alura - Especialização BackEnd


## 🧰 Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Security  
- Spring Data JPA  
- MySQL  
- JWT  

## 🔐 Autenticação

A autenticação é baseada em JWT. Após o login, o token JWT deve ser incluído no cabeçalho de cada requisição protegida:

```http
Authorization: Bearer <seu_token_aqui>
````

## 📌 Endpoints

### 🔑 Autenticação

| Método | Caminho  | Descrição                              |
| ------ | -------- | -------------------------------------- |
| POST   | `/login` | Autentica um usuário e retorna um JWT. |


### 👤 Usuários

| Método | Caminho     | Descrição            |
| ------ | ----------- | -------------------- |
| POST   | `/usuarios` | Cadastra um usuário. |


### 📄 Tópicos

| Método | Caminho         | Descrição                     |
| ------ | --------------- | ----------------------------- |
| POST   | `/topicos`      | Cria um novo tópico.          |
| GET    | `/topicos`      | Lista todos os tópicos.       |
| GET    | `/topicos/{id}` | Detalha um tópico específico. |
| PUT    | `/topicos/{id}` | Atualiza um tópico.           |
| DELETE | `/topicos/{id}` | Remove um tópico.             |


### 💬 Respostas

| Método | Caminho                         | Descrição                             |
| ------ | ------------------------------- | ------------------------------------- |
| POST   | `/topicos/{id}/respostas`       | Cria uma nova resposta para o tópico. |
| GET    | `/topicos/{id}/respostas`       | Lista todas as respostas do tópico.   |
| GET    | `/topicos/{id}/respostas/{id2}` | Detalha uma resposta específica.      |
| PUT    | `/topicos/{id}/respostas/{id2}` | Atualiza uma resposta.                |
| DELETE | `/topicos/{id}/respostas/{id2}` | Remove uma resposta.                  |


## ⚙️ Como Rodar o Projeto

### Pré-requisitos

* Java 17+
* Maven
* MySQL

### Configuração do Banco de Dados

1. Crie o banco de dados no MySQL:

```sql
CREATE DATABASE forumhub_api CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. No arquivo `application.properties`, configure o acesso ao banco:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub_api
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Rodando a Aplicação

1. Clone o repositório ou extraia o `.zip`.
2. No terminal, acesse a raiz do projeto.
3. Execute:

```bash
./mvnw spring-boot:run
```

Ou no Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:
👉 `http://localhost:8080`



## 📎 Licença

Este projeto é apenas educacional, desenvolvido como parte de estudos sobre APIs RESTful com Spring Boot.
