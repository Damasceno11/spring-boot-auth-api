# API RESTful Profissional com Spring Boot, PostgreSQL e Segurança JWT

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3+-green?style=for-the-badge&logo=spring) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql) ![Docker](https://img.shields.io/badge/Docker-blue?style=for-the-badge&logo=docker) ![Render](https://img.shields.io/badge/Render-46E3B7?style=for-the-badge&logo=render) ![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)

<p align="center">
  <a href="#-visão-geral-do-projeto">Visão Geral</a> •
  <a href="#-diagrama-da-arquitetura">Arquitetura</a> •
  <a href="#-princípios-e-padrões">Princípios</a> •
  <a href="#-funcionalidades-principais">Funcionalidades</a> •
  <a href="#-stack-de-tecnologias">Stack</a> •
  <a href="#-executando-localmente">Execução</a> •
  <a href="#-contrato-da-api">API</a> •
  <a href="#-desafios-de-implementação-e-deploy">Desafios</a> •
  <a href="#-autor">Autor</a>
</p>

---

## 🎯 Visão Geral do Projeto

Esta API RESTful é o resultado de um estudo aprofundado sobre a construção de aplicações backend robustas, seguras e escaláveis com o ecossistema Spring. O projeto vai além de um simples CRUD, implementando uma arquitetura em camadas, autenticação stateless com JWT, autorização baseada em perfis (Roles), e um tratamento de erros profissional.

O objetivo foi aplicar na prática os princípios de **Clean Architecture** e **SOLID**, resultando em um código desacoplado, testável e de fácil manutenção, que culminou no deploy bem-sucedido da aplicação e seu banco de dados na plataforma de nuvem **Render**.

## 🏗️ Diagrama da Arquitetura

O diagrama abaixo ilustra o fluxo de uma requisição através dos componentes da aplicação, desde o cliente até o banco de dados, no ambiente de produção na Render.

```mermaid
graph TD
    A[Cliente API <br/>(Postman/Frontend)];

    subgraph "Infraestrutura na Nuvem (Render)"
        B[Load Balancer];
        C[Web Service <br/>(Container Docker)];
        H[(Banco de Dados <br/>PostgreSQL)];
    end

    subgraph "Aplicação Spring Boot"
        D{Filtro de Segurança <br/>(JwtAuthFilter)};
        E[Controller <br/>(DTOs)];
        F[Service <br/>(Lógica de Negócio)];
        G[Repository <br/>(JPA/Hibernate)];
    end

    A -- HTTPS --> B;
    B --> C;
    C -- Requisição --> D;
    D -- Token Válido --> |Sim, Autenticado| E;
    D -- Token Inválido --> |Não| X(Erro 401/403);
    E --> F;
    F --> G;
    G <--> H;

## 📐 Princípios e Padrões

-   **Clean Architecture:** A aplicação é estritamente dividida em camadas de responsabilidade (Controller, Service, Repository), com um fluxo de dependência unidirecional, garantindo o desacoplamento e a testabilidade.
-   **SOLID:** O **Princípio da Responsabilidade Única (S)** é evidente em classes como `JwtService`, `UsuarioMapper` e nos componentes de cada camada, onde cada um possui uma única e bem definida razão para existir.
-   **Injeção de Dependência (DI):** Utilizada extensivamente pelo Spring para gerenciar o ciclo de vida dos componentes e promover o baixo acoplamento.
-   **API Contract First com DTOs:** O contrato da API é definido por classes DTO (`record`), desacoplando a representação externa dos dados do modelo de persistência interno (`@Entity`), o que aumenta a segurança e a flexibilidade.

## ✨ Funcionalidades Principais

-   ✅ **CRUD Completo:** Operações de Criar, Ler, Atualizar e Deletar para a entidade de Usuários.
-   🔐 **Segurança com Spring Security:**
    -   **Autenticação via JWT:** Geração de token no login para acesso *stateless*.
    -   **Autorização Baseada em Perfis:** Diferenciação de acesso entre usuários `ROLE_USER` e `ROLE_ADMIN`.
-   🧱 **Padrão DTO (Data Transfer Object):** Contratos de API bem definidos para Request e Response.
-   🚨 **Tratamento de Exceções Global:** Respostas de erro padronizadas e amigáveis utilizando `@RestControllerAdvice`.
-   📝 **Validação de Dados:** Utilização da especificação Bean Validation (`@Valid`).
-   🔗 **Relacionamentos JPA/Hibernate:** Mapeamento de `OneToMany` e `ManyToMany` com estratégias de `JOIN FETCH` para otimização de consultas.
-   🔑 **Gestão de Segredos Profissional:** Externalização de dados sensíveis (como a chave secreta do JWT) utilizando variáveis de ambiente.
-   ☁️ **Deploy na Nuvem:** Aplicação containerizada com **Docker** e implantada na plataforma **Render**, incluindo um banco de dados PostgreSQL gerenciado.

## 🛠️ Stack de Tecnologias

-   **Backend:** Java 17+, Spring Boot, Spring Security, Spring Data JPA / Hibernate, Lombok
-   **Banco de Dados:** PostgreSQL
-   **Build & Deploy:** Maven, Docker, Render

## 🚀 Executando Localmente

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/Damasceno11/spring-boot-auth-api.git](https://github.com/Damasceno11/spring-boot-auth-api.git)
    cd spring-boot-auth-api
    ```

2.  **Configure o Banco de Dados:**
    -   Certifique-se de ter o PostgreSQL rodando. Crie um banco de dados (ex: `crud_spring_db`).
    -   No arquivo `src/main/resources/application.properties`, ajuste as credenciais do seu banco local.

3.  **Configure a Variável de Ambiente (JWT Secret Key):**
    -   Na sua IDE (IntelliJ), vá em `Run -> Edit Configurations...`.
    -   Selecione a configuração Maven `Run Spring Boot App`.
    -   Na aba `Runner`, adicione uma `Environment variable`:
        -   **Nome:** `JWT_SECRET_KEY`
        -   **Valor:** `YmQ2Y2ZkYWEtN2I0NC00N2RkLWEzYTgtNTA5YzU3NzBhY2M3LWRiNmMzZjEwLTU0ODMtNDIyNy05NjZkLTQxM2U1MDIxOWZmNQ==`

4.  **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```
    A API estará disponível em `http://localhost:8080`. O `DataLoader` irá popular o banco com usuários e dados iniciais, incluindo um usuário admin (`alice123` / `senhaAlice`).

## 📡 Contrato da API

| Método | URL | Descrição | Corpo (Request) | Corpo (Response) | Acesso |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Autentica um usuário e retorna um token. | `LoginRequestDTO` | `LoginResponseDTO` | **Público** |
| `GET` | `/api/v1/usuarios` | Lista todos os usuários. | N/A | `List<UsuarioResponseDTO>`| **ADMIN** |
| `GET` | `/api/v1/usuarios/{id}` | Busca um usuário por ID. | N/A | `UsuarioResponseDTO` | **ADMIN** |
| `POST` | `/api/v1/usuarios` | Cria um novo usuário. | `UsuarioRequestDTO` | `UsuarioResponseDTO` | **ADMIN** |
| `PUT` | `/api/v1/usuarios/{id}` | Atualiza um usuário. | `UsuarioRequestDTO` | `UsuarioResponseDTO` | **ADMIN** |
| `DELETE`| `/api/v1/usuarios/{id}`| Deleta um usuário. | N/A | `204 No Content` | **ADMIN** |

---

## 🧠 Desafios de Implementação e Deploy

Esta seção documenta os desafios técnicos encontrados e as soluções profissionais aplicadas, registrando a jornada de aprendizado.

<details>
  <summary><strong>⚠️ Desafio 1: <code>StackOverflowError</code> em Relacionamentos Bidirecionais com Lombok</strong></summary>

  <br>

- **Problema:** A anotação `@Data` do Lombok, quando usada em entidades com relacionamentos bidirecionais (ex: `Usuario` <-> `Produto`), gera métodos `hashCode()` e `equals()` que entram em um loop de recursão infinita, um chamando o outro, resultando em um `StackOverflowError`.

- **Solução:** O ciclo foi quebrado instruindo o Lombok a excluir os campos de relacionamento da geração desses métodos, utilizando as anotações `@EqualsAndHashCode.Exclude` e `@ToString.Exclude`.

  ```java
  // Em Usuario.java, no campo 'produtos'
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Produto> produtos = new HashSet<>();
  ```
</details>

<details>
  <summary><strong>⚡ Desafio 2: Performance (<code>ConcurrentModificationException</code> e Problema N+1)</strong></summary>

  <br>

- **Problema:** A serialização de entidades com coleções *Lazy Loading* entrava em conflito com o Hibernate. A solução ingênua (`FetchType.EAGER`) levaria ao grave problema de performance N+1 selects.

- **Solução:** Implementamos consultas JPQL customizadas com **`JOIN FETCH`**. Isso instrui o Hibernate a buscar a entidade principal e suas coleções associadas em uma única e eficiente consulta SQL, garantindo que os dados estejam prontos antes da serialização.

  ```java
  // Em UsuarioRepository.java
  @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.enderecos LEFT JOIN FETCH u.produtos WHERE u.id = :id")
  Optional<Usuario> findByIdWithRelationships(Long id);
  ```
</details>

<details>
  <summary><strong>📦 Desafio 3: Duplicação de Dados com Múltiplos JOINs</strong></summary>

  <br>

- **Problema:** A query com `JOIN FETCH` para múltiplas coleções (ex: `enderecos` e `produtos`) gerava um produto cartesiano no resultado do SQL, fazendo com que itens em coleções do tipo `List` aparecessem duplicados no JSON.

- **Solução:** A estrutura de dados na entidade foi alterada de `List` para `Set`. A propriedade matemática do `Set` de não permitir elementos duplicados resolveu o problema elegantemente na camada de persistência, fazendo o Hibernate descartar as duplicatas ao montar os objetos.

  ```java
  // Em Usuario.java
  @OneToMany(mappedBy = "usuario", ...)
  private Set<Endereco> enderecos = new HashSet<>(); // Trocado de List para Set
  ```
</details>

<details>
  <summary><strong>🚀 Desafio 4: Deploy na Nuvem e Detecção de Ambiente (Render)</strong></summary>

  <br>

- **Problema:** O deploy na Render falhou por múltiplos motivos:
    1.  A plataforma não detectou o projeto como `Java` devido a uma estrutura de repositório Git inicial que continha outros tipos de arquivos na raiz.
    2.  A conexão com o banco de dados falhou (`Unable to determine Dialect`) devido a incompatibilidades entre a URL de conexão da Render e o formato esperado pelo driver JDBC, além de nomes incorretos de variáveis de ambiente.

- **Solução:**
    1.  O repositório foi reestruturado para ter o projeto Java na raiz.
    2.  Adotamos o **Docker** como ambiente de deploy, criando um `Dockerfile` multi-estágio. Isso tornou o build previsível e independente da detecção da plataforma.
    3.  Corrigimos as variáveis de ambiente na Render para usar os nomes exatos que o Spring Boot espera (`SPRING_DATASOURCE_USERNAME`, etc.) e ajustamos a URL do banco para o formato `jdbc:postgresql://...`, garantindo a conexão.

  ```dockerfile
  # Dockerfile (Estágio 2 - Run)
  FROM openjdk:17-jdk-slim
  WORKDIR /app
  COPY --from=build /app/target/crud-usuario-postgress-0.0.1-SNAPSHOT.jar app.jar
  EXPOSE 10000
  ENTRYPOINT ["java", "-jar", "app.jar"]
  ```
</details>

<br>

## 👨‍💻 Autor

Desenvolvido com dedicação por **Pedro Damasceno**.

-   **GitHub:** [@Damasceno11](https://github.com/Damasceno11)
-   **LinkedIn:** [Pedro Damasceno](https://www.linkedin.com/in/pedro-damasceno-23b330150/)
-   **Email:** <pedropaulodamasceno@gmail.com>

Obrigado por visitar o repositório!
