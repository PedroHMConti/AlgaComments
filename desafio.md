\# Implemente o sistema: AlgaComments



Você deve desenvolver um sistema composto por dois microsserviços que se comunicam de forma \*\*síncrona\*\* via HTTP/REST usando Spring `RestClient`. O sistema será responsável por receber comentários de usuários, validá-los contra palavras proibidas e armazenar apenas os aprovados.



O sistema deve permitir:



\- Criar um novo comentário e enviar ele para moderação

\- Consultar os detalhes de um comentário aprovado

\- Listar comentários aprovados com paginação



\## 1. Microsserviço: CommentService



\- Expor uma API REST para criação e consulta de comentários

\- Enviar novos comentários para moderação via POST síncrono usando `RestClient`

\- Armazenar apenas comentários aprovados



\### Endpoints



\- `POST /api/comments`

\- `GET /api/comments/{id}`

\- `GET /api/comments`



\### Controller `CommentController`



\#### `POST /api/comments`:



\- Cria um novo comentário

\- Recebe: `CommentInput`

\- Retorna:

&#x20; - \*\*201 Created\*\* com `CommentOutput` se aprovado

&#x20; - \*\*422 Unprocessable Entity\*\* com motivo se rejeitado



\#### `GET /api/comments/{id}`:



\- Retorna \*\*200 OK\*\* com `CommentOutput` se existir

\- Retorna \*\*404 Not Found\*\* se não existir



\#### `GET /api/comments`:



\- Retorna lista paginada de comentários aprovados

\- Parâmetros: `page`, `size`

\- Estrutura de resposta:



```json

{

&#x20; "page": 0,

&#x20; "size": 10,

&#x20; "totalElements": 45,

&#x20; "totalPages": 5,

&#x20; "content": \[ /\* lista de CommentOutput \*/ ]

}

```



\### DTOs



\#### `CommentInput` - Modelo para criação:



```json

{

&#x20; "text": "string",

&#x20; "author": "string"

}

```



\#### `CommentOutput` - Modelo de exibição:



```json

{

&#x20; "id": "string", //UUID

&#x20; "text": "string",

&#x20; "author": "string",

&#x20; "createdAt": "2023-11-15T10:00:00Z"

}

```



\### Integração com o ModerationService



O `CommentService` faz uma chamada POST para `/api/moderate` do `ModerationService` com o corpo:



```json

{

&#x20; "text": "string",

&#x20; "commentId": "string" //UUID

}

```



Resposta esperada:



```json

{

&#x20; "approved": true,

&#x20; "reason": "string"

}

```



\### Regras de Validação



\- `id` deve ser UUID

\- Comentários rejeitados \*\*não são armazenados\*\*



\### Requisitos Técnicos



\- Use H2 para persistência

\- Configure timeout de 5 segundos para chamadas ao `ModerationService`

\- Use `RestClient` para comunicação síncrona

\- Trate adequadamente:

&#x20; - Erros gerais na integração (retorne 502)

&#x20; - Erros de timeout na integração (retorne 504)

&#x20; - Comentário não encontrado (404)



\## 2. Microsserviço: ModerationService



\- Expor um endpoint REST para validação de comentários

\- Validar se o texto contém palavras proibidas (lista fixa)



\### Endpoint



\- `POST /api/moderate`



\### Controller `ModerationController`



\#### `POST /api/moderate`:



\- Verifica se o texto enviado possui palavras proibidas

\- Recebe: `ModerationInput`

\- Retorna:

&#x20; - \*\*200 OK\*\* com `ModerationOutput` se aprovado ou reprovado



\### DTOs



\#### `ModerationInput` - Modelo para solicitação:



```json

{

&#x20; "text": "string",

&#x20; "commentId": "string" //UUID

}

```



\#### `ModerationOutput` - Modelo de resultado:



```json

{

&#x20; "approved": true,

&#x20; "reason": "string"

}

```



\### Regras de Validação



\- Lista fixa de palavras proibidas no `ModerationService`: `\["ódio", "xingamento"]`



\### Requisitos Técnicos



\- Mantenha a lista de palavras proibidas em memória



\## 3. Tarefas do Desafio



1\. Implemente o `CommentService` com:

&#x20;  - Endpoints REST

&#x20;  - Integração síncrona com `ModerationService`

&#x20;  - Persistência de comentários aprovados

2\. Implemente o `ModerationService` com:

&#x20;  - Endpoint POST `/api/moderate`

&#x20;  - Validação de palavras proibidas

3\. Configure o `RestClient`:

&#x20;  - Read timeout de 5 segundos

&#x20;  - Tratamento de erros

4\. Garanta que:

&#x20;  - Respostas HTTP seguem os códigos adequados

5\. Teste os cenários:

&#x20;  - Comentário válido

&#x20;  - Comentário com palavras proibidas

&#x20;  - Timeout na moderação

&#x20;  - Consulta de comentário inexistente



\## Dicas



\- Registre logs nas operações importantes

\- Opte pela abordagem de implementação de RestClient que desejar

