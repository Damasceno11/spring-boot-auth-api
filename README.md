# API RESTful Profissional com Spring Boot, PostgreSQL e Segurança JWT

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql) ![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)

<p align="center">
  <a href="#-descrição-do-projeto">Descrição</a> •
  <a href="#-funcionalidades-principais">Funcionalidades</a> •
  <a href="#-tecnologias-utilizadas">Tecnologias</a> •
  <a href="#-como-executar-o-projeto">Como Executar</a> •
  <a href="#-endpoints-da-api">API Endpoints</a> •
  <a href="#-desafios-e-soluções">Desafios</a> •
  <a href="#-autor">Autor</a>
</p>

---

## 📄 Descrição do Projeto

Esta é uma API RESTful completa desenvolvida como um projeto de estudo aprofundado sobre as melhores práticas de arquitetura de software e desenvolvimento backend com o ecossistema Spring. A aplicação implementa um sistema de CRUD (Create, Read, Update, Delete) para usuários, endereços e produtos, com um sistema de segurança robusto baseado em JSON Web Tokens (JWT) e autorização por perfis de acesso (Roles).

O foco principal foi a construção de um código limpo, de fácil manutenção e seguindo princípios como SOLID e Clean Architecture, utilizando o padrão DTO para o contrato da API e garantindo a segurança e a integridade dos dados.

## ✨ Funcionalidades Principais

-   ✅ **CRUD Completo:** Operações de Criar, Ler, Atualizar e Deletar para a entidade de Usuários.
-   🔐 **Segurança com Spring Security:**
    -   **Autenticação via JWT:** Geração de token no login para acesso *stateless*.
    -   **Autorização Baseada em Perfis:** Diferenciação de acesso entre usuários `ROLE_USER` e `ROLE_ADMIN`.
-   🧱 **Padrão DTO (Data Transfer Object):** Contratos de API bem definidos para Request e Response, garantindo que dados sensíveis (como senhas) nunca sejam expostos.
-   🚨 **Tratamento de Exceções Global:** Respostas de erro padronizadas e amigáveis para o cliente da API, utilizando `@RestControllerAdvice`.
-   📝 **Validação de Dados:** Validação dos dados de entrada utilizando a especificação Bean Validation.
-   🔗 **Relacionamentos JPA/Hibernate:** Mapeamento de relacionamentos `OneToMany` e `ManyToMany`.
-   🔑 **Gestão de Segredos Profissional:** Externalização de dados sensíveis (como a chave secreta do JWT) utilizando variáveis de ambiente.

## 🛠️ Tecnologias Utilizadas

-   **Backend:**
    -   Java 17+
    -   Spring Boot 3
    -   Spring Security
    -   Spring Data JPA / Hibernate
    -   Lombok
-   **Banco de Dados:**
    -   PostgreSQL
-   **Gerenciamento de Dependências:**
    -   Maven

## 🚀 Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/Damasceno11/spring-boot-crud-auth-api.git](https://github.com/Damasceno11/spring-boot-crud-auth-api.git)
    cd spring-boot-crud-auth-api
    ```

2.  **Configure o Banco de Dados:**
    -   Certifique-se de ter o PostgreSQL rodando.
    -   Crie um banco de dados (ex: `crud_spring_db`).
    -   No arquivo `src/main/resources/application.properties`, ajuste as seguintes propriedades com suas credenciais:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/crud_spring_db
        spring.datasource.username=seu_usuario
        spring.datasource.password=sua_senha
        ```

3.  **Configure a Variável de Ambiente (JWT Secret Key):**
    -   Este passo é **essencial** para a segurança funcionar.
    -   Na sua IDE (ex: IntelliJ), vá em `Run -> Edit Configurations...`.
    -   Encontre sua configuração `CrudUsuarioPostgressApplication`.
    -   No campo `Environment variables`, adicione a seguinte variável:
        -   **Nome:** `JWT_SECRET_KEY`
        -   **Valor:** `YmQ2Y2ZkYWEtN2I0NC00N2RkLWEzYTgtNTA5YzU3NzBhY2M3LWRiNmMzZjEwLTU0ODMtNDIyNy05NjZkLTQxM2U1MDIxOWZmNQ==`

4.  **Execute a aplicação:**
    -   Você pode rodar pela sua IDE ou via terminal com o Maven:
    ```bash
    mvn spring-boot:run
    ```
    A API estará disponível em `http://localhost:8080`. O `DataLoader` irá popular o banco com usuários e dados iniciais, incluindo um usuário admin (`alice123` / `senhaAlice`).

## 📡 Endpoints da API

| Método   | URL                       | Descrição                          | Acesso      |
| :------- | :------------------------ | :--------------------------------- | :---------- |
| `POST`   | `/api/auth/login`         | Autentica um usuário e retorna um token JWT. | **Público** |
| `GET`    | `/api/v1/usuarios`        | Lista todos os usuários.           | **ADMIN** |
| `GET`    | `/api/v1/usuarios/{id}`   | Busca um usuário por ID.           | **ADMIN** |
| `POST`   | `/api/v1/usuarios`        | Cria um novo usuário.              | **ADMIN** |
| `PUT`    | `/api/v1/usuarios/{id}`   | Atualiza um usuário existente.     | **ADMIN** |
| `DELETE` | `/api/v1/usuarios/{id}`   | Deleta um usuário.                 | **ADMIN** |

---

## 🧠 Desafios e Soluções

Durante o desenvolvimento deste projeto, enfrentamos desafios clássicos do ecossistema Spring/JPA. Esta seção documenta os problemas e as soluções profissionais aplicadas, servindo como um registro do processo de aprendizado.

<details>
  <summary><strong>⚠️ Desafio 1: <code>StackOverflowError</code> em Relacionamentos Bidirecionais</strong></summary>
  
  <br>

  - **O Problema:** Ao usar a anotação `@Data` do Lombok em entidades com relacionamentos bidirecionais (ex: `Usuario` <-> `Produto`), os métodos `hashCode()` e `equals()` gerados automaticamente entravam em um loop infinito, um chamando o outro, resultando em um `StackOverflowError`.
  
  - **A Solução:** Utilizamos as anotações `@EqualsAndHashCode.Exclude` e `@ToString.Exclude` nos atributos de relacionamento para instruir o Lombok a ignorar esses campos durante a geração dos métodos, quebrando assim o ciclo de recursão.

    ```java
    // Em Usuario.java
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Produto> produtos = new HashSet<>();
    ```
</details>

<details>
  <summary><strong>⚡ Desafio 2: <code>ConcurrentModificationException</code> e o Problema N+1</strong></summary>
  
  <br>

  - **O Problema:** Ao tentar serializar a lista de usuários para JSON, a biblioteca Jackson entrava em conflito com o carregamento preguiçoso (*Lazy Loading*) do Hibernate, causando uma `ConcurrentModificationException`. A solução ingênua (`FetchType.EAGER`) levaria ao grave problema de performance N+1 selects.
  
  - **A Solução:** Implementamos a abordagem profissional utilizando uma query JPQL com **`JOIN FETCH`**. Isso instrui o Hibernate a buscar a entidade principal e suas coleções associadas em uma única e eficiente consulta ao banco de dados, garantindo que os dados estejam totalmente carregados antes da serialização.
    
    ```java
    // Em UsuarioRepository.java
    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.enderecos LEFT JOIN FETCH u.produtos WHERE u.id = :id")
    Optional<Usuario> findByIdWithRelationships(Long id);
    ```
</details>

<details>
  <summary><strong>📦 Desafio 3: Duplicação de Dados em Coleções com Múltiplos JOINs</strong></summary>
  
  <br>

  - **O Problema:** A query com `JOIN FETCH` para múltiplas coleções (ex: `enderecos` e `produtos`) gerava um produto cartesiano no resultado do SQL, fazendo com que itens em coleções do tipo `List` aparecessem duplicados no JSON final.
  
  - **A Solução:** Alteramos a estrutura de dados da coleção na entidade de `List` para `Set`. A natureza do `Set` de não permitir elementos duplicados resolveu o problema elegantemente, fazendo com que o Hibernate descartasse as duplicatas automaticamente ao reconstruir os objetos.

    ```java
    // Em Usuario.java
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>(); // Trocado de List para Set
    ```
</details>

<br>

## 👨‍💻 Autor

Feito com dedicação por **Pedro Damasceno**.

-   **GitHub:** [@Damasceno11](https://github.com/Damasceno11)
-   **LinkedIn:** [Pedro Damasceno](https://www.linkedin.com/in/pedro-damasceno-23b330150/)
-   **Email:** <pedropaulodamasceno@gmail.com>

Agradeço pela visita ao repositório!
