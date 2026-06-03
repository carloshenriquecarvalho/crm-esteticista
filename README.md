# Pimenta Estética CRM - Backend API

API RESTful desenvolvida em Java com Spring Boot para o gerenciamento de clínicas de estética. O sistema permite o controle de usuários, pacientes, esteticistas, procedimentos e agendamentos, utilizando uma arquitetura robusta com segurança JWT e controle de versão de banco de dados com o Flyway.

## Stack Tecnológica

* **Linguagem:** Java 21+
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security 6 + Auth0 JWT
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL (Produção) / H2 Database (Testes de Integração)
* **Documentação:** Springdoc OpenAPI (Swagger)
* **Validação:** Hibernate Validator
* **Infraestrutura:** Docker para conteinerização do projeto e Flyway para migrations (versionamento do banco de dados).

---

## Arquitetura e Soluções Técnicas

O projeto foi construído focando em eficiência e segurança de dados, implementando as seguintes abordagens técnicas:

### 1. Serialização de Entidades
Para o MVP, a API trafega entidades de domínio cruas. Para evitar o clássico loop infinito de serialização em relacionamentos bidirecionais (ex: `User` -> `Patient` -> `User`), o sistema utiliza um mapeamento rigoroso com o Jackson:
* `@JsonManagedReference`: Nas coleções da entidade pai, como a clase `User`.
* `@JsonBackReference`: Nas chaves estrangeiras das entidades filhas, como `Patient` e `Appointment`, nomeando cada referência para evitar conflitos de `InvalidDefinitionException`.

Contudo, hoje, na última atualização (03/06/2026), o projeto conta com a implementação de DTOs de forma mais profissional tanto para os requests quanto para as responses (bem como também para loginRequest e loginResponse e registerRequest e registerResponse).

### 2. Autenticação Stateless (JWT)
O controle de acesso é Stateless.
* Endpoints `/auth/login` e `/auth/register` utilizam DTOs (Records) validados.
* Senhas são hasheadas com `BCryptPasswordEncoder`.
* As requisições protegidas passam por um `SecurityFilter` customizado (`OncePerRequestFilter`) que valida a assinatura do token HMAC256 e injeta a autorização no `SecurityContextHolder`.

### 3. Prevenção contra IDOR (Insecure Direct Object Reference)
A API não confia em IDs de usuários passados pelo frontend via URL ou corpo da requisição. Todos os controllers operacionais (ex: `PatientController`) extraem a identidade do operador diretamente do token validado utilizando a anotação `@AuthenticationPrincipal`. Buscas no banco exigem prova de propriedade (`findByIdAndUserId`).

### 4. Database Migrations (Flyway)
O controle estrutural do PostgreSQL foi retirado do `spring.jpa.hibernate.ddl-auto` e delegado ao Flyway. Isso garante previsibilidade e previne perda de dados em alterações de schema e adição de constraints.

---

## Etapas de Desenvolvimento

1.  **Modelagem de Domínio:** Criação das entidades base (`User`, `Patient`, `Beautician`, `Procedure`, `Appointment`) e mapeamento ORM das relações.
2.  **Resolução de Dependências Cíclicas:** Ajuste das anotações do Jackson para serialização de JSON limpa.
3.  **Segurança e JWT:** Configuração do Spring Security 6, filtros e serviços de token.
4.  **Tratamento de Exceções:** Liberação da rota `/error` no `SecurityConfig` para visualização real de violações de banco de dados e validação.
5.  **Refatoração de Controllers:** Remoção de IDs nas URLs de criação e implementação do `@AuthenticationPrincipal` para queries blindadas.
6.  **Testes de Integração:** Setup do H2 Database e uso do `MockMvc` para simulação completa de requisições HTTP e validação de regras de negócio em memória.
7.  **Versionamento e Documentação:** Implementação do Flyway para DDL e do Swagger UI para exploração interativa da API.

---

## Situação Atual e Próximos Passos

O MVP está funcional, seguro e testado. O fluxo central de negócios (Cadastro -> Autenticação -> Criação de Cadastros Base -> Agendamento) opera de ponta a ponta sem vazamento de dados entre usuários.

**Roadmap (Próxima Sprint):**
* **Pivotagem de Domínio (`Clinic`):** O nó central dos relacionamentos deixará de ser o `User`. Será criada a entidade `Clinic`, transformando o `User` em um membro da equipe/operador, o que permitirá múltiplas clínicas e gestão de níveis de acesso (RBAC) mais complexa.
* **Tratamento Global de Erros:** Implementar um `@RestControllerAdvice` para capturar `DataIntegrityViolationException` e `MethodArgumentNotValidException`, padronizando o JSON de resposta de erros (RFC 7807 - Problem Details).

---