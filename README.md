# E-commerce - API REST com Spring Boot - Em desenvolvimento

## Descrição
Esta API foi desenvolvida como parte de um projeto de portfólio, com o objetivo de criar uma plataforma de E-commerce que oferece funcionalidades completas para gerenciamento de produtos, pedidos, usuários, e outras operações essenciais. A aplicação segue as melhores práticas do modelo REST, utiliza validações de regras de negócio e implementa diferentes arquiteturas como MVC e Clean Architecture. Além disso, a API suporta autenticação com múltiplos fatores e integração com bancos de dados relacionais e não-relacionais.

## Funcionalidades

### Usuários
- Cadastro de usuários com informações de contato e endereço.
- Autenticação e autorização de usuários com suporte a múltiplos fatores.
- Atualização e gerenciamento de perfis de usuário.

### Produtos
- Cadastro e gerenciamento de produtos com informações detalhadas, incluindo nome, descrição, preço, estoque, e categoria.
- Listagem de produtos com suporte a filtros por categoria, preço, e outros atributos.
- Controle de estoque para garantir a disponibilidade dos produtos.

### Carrinhos de Compras
- Criação e gerenciamento de carrinhos de compras para cada usuário.
- Adição e remoção de produtos no carrinho de compras.
- Cálculo dinâmico de preços totais no carrinho de compras.

### Pedidos
- Criação de pedidos com base no conteúdo do carrinho de compras.
- Atualização do status do pedido (e.g., Pendente, Processando, Enviado, Concluído).
- Histórico de pedidos para usuários registrados.

### Pagamentos
- Integração com métodos de pagamento como Stripe e PayPal.
- Processamento seguro de pagamentos com suporte a múltiplas moedas.
- Armazenamento e gerenciamento de informações de pagamento e status.

### Endereços
- Armazenamento de múltiplos endereços para cada usuário.
- Suporte a diferentes tipos de endereços (e.g., Residencial, Comercial).

## Tecnologias Utilizadas
- **Spring Boot Starter Data JPA**: Facilita o acesso e a persistência de dados usando o JPA (Java Persistence API).
- **Spring Boot Starter Security**: Fornece suporte para autenticação e autorização na aplicação.
- **Spring Boot Starter Validation**: Integra validações de entrada baseadas em anotações.
- **Spring Boot Starter Web**: Suporte para desenvolvimento de aplicativos web usando Spring MVC.
- **Flyway Core**: Gerencia migrações de banco de dados de forma programática.
- **Flyway MySQL**: Suporte específico para integração do Flyway com o MySQL.
- **Spring Boot DevTools**: Ferramentas de desenvolvimento que melhoram a experiência de desenvolvimento Spring Boot.
- **MySQL Connector/J**: Driver JDBC para comunicação com bancos de dados MySQL.
- **Lombok**: Biblioteca que simplifica a criação de classes Java reduzindo a verbosidade do código.
- **Spring Boot Starter Test**: Suporte para testes na aplicação Spring Boot.
- **Spring Security Test**: Suporte para testes de segurança na aplicação Spring.
- **Java JWT (com.auth0:java-jwt)**: Biblioteca para criação e validação de tokens JWT (JSON Web Token).
- **Springdoc OpenAPI Starter WebMvc UI**: Geração automática de documentação OpenAPI (anteriormente Swagger) para APIs Spring MVC.

## Configuração
1. Clone este repositório.
2. Navegue até o diretório do projeto.
3. Configure o arquivo `application.properties` para apontar para seu banco de dados MySQL.
4. Execute as migrações de banco de dados usando o Flyway.
5. Compile e execute a aplicação usando Maven ou Gradle.

```properties
# Exemplo de configuração de banco de dados no application.properties
spring.datasource.url=jdbc:mysql://localhost:<porta>/ecommerce
spring.datasource.username=usuario
spring.datasource.password=senha

# Configurações do Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
```

## Autenticação
A API utiliza autenticação baseada em tokens JWT (JSON Web Token). Cada usuário, após autenticação bem-sucedida, recebe um token JWT que deve ser incluído em todas as requisições subsequentes nos cabeçalhos de autorização.

Exemplo de configuração no `application.properties`:

```properties
# Configuração JWT
jwt.secret=seuSegredoJWT
jwt.expiration=3600000
```

Com o token JWT, as rotas protegidas podem ser acessadas:

```http
GET /api/protected/resource HTTP/1.1
Authorization: Bearer {seu_token_jwt}
```

## Licença
Este projeto está licenciado sob a Licença Apache 2.0 - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
