# AlgaComments

Este repositório contém a minha solução para o **desafio da aula 11.8 do Nível 1** do curso **Especialista em Microsserviços**, da [AlgaWorks](https://www.algaworks.com).

## Sobre o desafio

O desafio pede um sistema de comentários com moderação automática, formado por **dois microsserviços** que se comunicam de forma **síncrona via HTTP/REST** usando o Spring `RestClient`.

A ideia é simples: o usuário envia um comentário, ele passa por uma moderação que verifica se o texto contém palavras proibidas, e só os comentários aprovados são armazenados.

### CommentService

Recebe e expõe os comentários. É responsável por:

- criar comentários e enviá-los para moderação (`POST /api/comments`);
- consultar um comentário aprovado pelo ID (`GET /api/comments/{id}`);
- listar os comentários aprovados com paginação (`GET /api/comments`);
- persistir apenas os comentários aprovados, usando H2.

Além disso, a integração com o serviço de moderação deve ter timeout de 5 segundos e tratar as falhas com os códigos HTTP adequados: **422** para comentário reprovado, **404** para comentário inexistente, **502** para erros gerais na integração e **504** para timeout.

### ModerationService

Expõe o endpoint `POST /api/moderate`, que verifica o texto do comentário contra uma lista fixa de palavras proibidas, mantida em memória, e responde se ele foi aprovado ou não, junto com o motivo.

O enunciado completo está em [desafio.md](desafio.md).

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC e `RestClient`
- Spring Data JPA
- H2 Database
- Lombok
- Gradle

## Como executar

Suba primeiro o `ModerationService` (porta 8081) e depois o `CommentService` (porta 8080), cada um em um terminal:

```bash
cd ModerationService/ModerationService
./gradlew bootRun
```

```bash
cd CommentService/CommentService
./gradlew bootRun
```

> No Windows (PowerShell), use `.\gradlew.bat bootRun`.
