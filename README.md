<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3+-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white" />
  <img src="https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" />
</p>

<h1 align="center">💰 Controle Financeiro Pessoal</h1>

<p align="center">
  Aplicação Web completa para <strong>gestão de gastos semanais e mensais</strong>, desenvolvida com <strong>Java + Spring Boot</strong>.<br/>
  Dashboard interativo estilo Power BI com gráficos dinâmicos e interface responsiva.
</p>

<p align="center">
  <a href="#-funcionalidades">Funcionalidades</a> •
  <a href="#%EF%B8%8F-arquitetura">Arquitetura</a> •
  <a href="#-tecnologias">Tecnologias</a> •
  <a href="#-estrutura-do-projeto">Estrutura</a> •
  <a href="#-como-executar">Como Executar</a> •
  <a href="#-endpoints-da-api">API</a> •
  <a href="#-autor">Autor</a>
</p>

---

## 📋 Sobre o Projeto

O **Controle Financeiro Pessoal** é uma aplicação web full-stack que permite ao usuário registrar, categorizar e acompanhar seus gastos de forma simples e visual. Com dashboards interativos inspirados no Power BI, o usuário tem uma visão clara de para onde seu dinheiro está indo — seja por semana ou por mês.

A aplicação conta com **autenticação segura via JWT**, **API REST completa** para integração com outros sistemas, e uma **interface moderna e responsiva** construída com Bootstrap 5 e Thymeleaf.

---

## 🚀 Funcionalidades

| Funcionalidade | Descrição |
|---|---|
| ✅ **Cadastro de Gastos** | Registre seus gastos com descrição, valor, categoria e data |
| ✅ **Visualização Semanal** | Acompanhe seus gastos organizados por semana |
| ✅ **Visualização Mensal** | Veja o resumo completo dos gastos de cada mês |
| ✅ **Dashboard Power BI** | Gráficos interativos com Chart.js (pizza, barras, linhas) |
| ✅ **API REST Completa** | Endpoints para CRUD de gastos e autenticação |
| ✅ **Integração MySQL** | Persistência robusta de dados com Spring Data JPA |
| ✅ **Autenticação JWT** | Login seguro com tokens JWT via Spring Security |
| ✅ **Validação de Cadastro** | Email válido + senha com maiúscula e caractere especial |
| ✅ **Interface Responsiva** | Layout adaptável para desktop, tablet e mobile |

---

## 🏗️ Arquitetura

O projeto segue uma **arquitetura em camadas** (Layered Architecture), garantindo separação de responsabilidades, manutenibilidade e escalabilidade:

```
┌─────────────────────────────────────────────┐
│              🌐 Frontend                     │
│         (Thymeleaf + Bootstrap 5)            │
├─────────────────────────────────────────────┤
│              🎮 Controller                   │
│    Recebe requisições HTTP e retorna views   │
├─────────────────────────────────────────────┤
│              ⚙️ Service                      │
│    Lógica de negócio e regras da aplicação   │
├─────────────────────────────────────────────┤
│              📦 Repository                   │
│    Acesso a dados com Spring Data JPA        │
├─────────────────────────────────────────────┤
│              🗄️ Database (MySQL)             │
│    Persistência e armazenamento de dados     │
└─────────────────────────────────────────────┘
```

### Camadas do Projeto

| Camada | Responsabilidade |
|---|---|
| **Controller** | Recebe requisições HTTP, valida entrada e delega ao Service |
| **Service** | Contém a lógica de negócio, validações e regras |
| **Repository** | Interface com o banco de dados via Spring Data JPA |
| **Model** | Entidades JPA que representam as tabelas do banco |
| **DTO** | Objetos de transferência de dados entre camadas |
| **Security** | Configuração de autenticação, filtros JWT e geração de tokens |
| **Config** | Configurações adicionais da aplicação |

---

## 🧰 Tecnologias

### Back-end
- **Java 17+** — Linguagem principal
- **Spring Boot 3+** — Framework para criação da aplicação
- **Spring Web** — Construção de APIs REST e controllers MVC
- **Spring Data JPA** — Abstração para acesso a dados com Hibernate
- **Spring Security** — Framework de segurança e autenticação
- **JWT (JSON Web Token)** — Autenticação stateless baseada em tokens
- **Maven** — Gerenciamento de dependências e build

### Front-end
- **Thymeleaf** — Template engine para renderização server-side
- **Bootstrap 5** — Framework CSS para layout responsivo
- **Chart.js** — Biblioteca JavaScript para gráficos interativos

### Banco de Dados
- **MySQL** — Banco de dados relacional

---

## 📂 Estrutura do Projeto

```
controle-financeiro/
│
├── 📁 src/main/java/com/controle/financeiro/
│   │
│   ├── 📁 controller/
│   │   ├── GastoController.java        # Endpoints de gastos (CRUD + views)
│   │   └── AuthController.java         # Endpoints de autenticação (login/registro)
│   │
│   ├── 📁 service/
│   │   ├── GastoService.java           # Lógica de negócio para gastos
│   │   └── UserService.java            # Lógica de negócio para usuários
│   │
│   ├── 📁 repository/
│   │   ├── GastoRepository.java        # Interface JPA para entidade Gasto
│   │   └── UserRepository.java         # Interface JPA para entidade User
│   │
│   ├── 📁 model/
│   │   ├── Gasto.java                  # Entidade JPA — representa um gasto
│   │   ├── User.java                   # Entidade JPA — representa um usuário
│   │   └── Categoria.java             # Enum com as categorias de gastos
│   │
│   ├── 📁 dto/
│   │   ├── GastoDTO.java              # DTO para transferência de dados de gasto
│   │   ├── LoginDTO.java             # DTO para dados de login
│   │   ├── RegisterDTO.java          # DTO para dados de registro (com validação)
│   │   └── TokenDTO.java             # DTO para resposta de token JWT
│   │
│   ├── 📁 security/
│   │   ├── SecurityConfig.java        # Configuração do Spring Security
│   │   ├── JwtFilter.java            # Filtro para interceptar e validar tokens JWT
│   │   └── JwtService.java           # Serviço para geração e validação de JWT
│   │
│   └── ControleFinanceiroApplication.java  # Classe principal
│
├── 📁 src/main/resources/
│   ├── 📁 templates/                  # Templates Thymeleaf (HTML)
│   ├── 📁 static/                     # Arquivos estáticos (CSS, JS, imagens)
│   └── application.properties         # Configurações da aplicação
│
├── 📁 .mvn/wrapper/                   # Maven Wrapper (não requer Maven instalado)
├── mvnw.cmd                           # Script Maven Wrapper para Windows
├── pom.xml                            # Dependências Maven
└── README.md                          # Documentação do projeto
```

---

## ▶️ Como Executar

### Pré-requisitos

- **Java 17** ou superior instalado → [Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **MySQL 8.0+** instalado e rodando → [Download](https://dev.mysql.com/downloads/mysql/)
- ⚠️ **Maven não é necessário** — o projeto inclui o Maven Wrapper

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/controle-financeiro.git
cd controle-financeiro
```

### 2. Configure o banco de dados

O banco será criado automaticamente (`createDatabaseIfNotExist=true`), apenas certifique-se de que o MySQL esteja rodando.

### 3. Configure o `application.properties`

Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3307/controle_financeiro?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT
jwt.secret=SUA_CHAVE_SECRETA_AQUI
jwt.expiration=86400000

# Thymeleaf
spring.thymeleaf.cache=false
```

> **Nota:** Ajuste a porta (`3307`), o `username` e `password` conforme sua instalação do MySQL.

### 4. Execute a aplicação

```bash
# Windows (PowerShell)
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

### 5. Acesse no navegador

```
http://localhost:8080
```

---

## 📡 Endpoints da API

### 🔐 Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/auth/login` | Realiza login e retorna token JWT |
| `POST` | `/api/auth/register` | Registra novo usuário |

**Exemplo de Login:**
```json
POST /api/auth/login
{
  "email": "usuario@email.com",
  "senha": "Senha@123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer"
}
```

---

### 💸 Gastos

> ⚠️ Todos os endpoints de gastos requerem autenticação. Envie o token JWT no header:
> ```
> Authorization: Bearer <seu_token_jwt>
> ```

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/gastos` | Lista todos os gastos do usuário |
| `GET` | `/api/gastos/{id}` | Busca um gasto por ID |
| `GET` | `/api/gastos/semana` | Lista gastos da semana atual |
| `GET` | `/api/gastos/mes` | Lista gastos do mês atual |
| `POST` | `/api/gastos` | Cadastra um novo gasto |
| `PUT` | `/api/gastos/{id}` | Atualiza um gasto existente |
| `DELETE` | `/api/gastos/{id}` | Remove um gasto |

**Exemplo de Cadastro de Gasto:**
```json
POST /api/gastos
{
  "descricao": "Almoço no restaurante",
  "valor": 45.90,
  "categoria": "ALIMENTACAO",
  "data": "2026-02-23"
}
```

**Resposta:**
```json
{
  "id": 1,
  "descricao": "Almoço no restaurante",
  "valor": 45.90,
  "categoria": "ALIMENTACAO",
  "data": "2026-02-23"
}
```

---

### 📊 Dashboard

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/dashboard` | Página do dashboard com gráficos interativos |
| `GET` | `/api/gastos/resumo/semanal` | Dados agregados por semana (JSON) |
| `GET` | `/api/gastos/resumo/mensal` | Dados agregados por mês (JSON) |

---

## 🔒 Segurança

A aplicação utiliza **Spring Security** com autenticação baseada em **JWT** e **sessão web**:

1. **Login Web** → O usuário faz login via formulário e é autenticado por sessão
2. **Login API** → O usuário envia credenciais para `/api/auth/login` e recebe um token JWT
3. **Filtro JWT** → O `JwtFilter` intercepta requisições API, valida o token e autentica o usuário
4. **Sessão + API** → As rotas `/api/**` aceitam tanto sessão web quanto tokens JWT
5. **Expiração** → Tokens JWT expiram após o tempo configurado (padrão: 24 horas)

### 🔐 Validações de Cadastro

| Campo | Regra |
|---|---|
| **Email** | Deve ser um email válido (ex: `usuario@dominio.com`) |
| **Senha** | Mínimo 6 caracteres + 1 letra maiúscula + 1 caractere especial (`@#$%&!*`) |

```
Cliente → POST /login (credenciais) → Servidor
Cliente ← Token JWT ← Servidor
Cliente → GET /api/gastos + Header: Bearer token → Servidor
Cliente ← Dados ← Servidor
```

---

## 📊 Dashboard — Visão Geral

O dashboard oferece visualizações interativas dos seus gastos:

- 📈 **Gráfico de Linha** — Evolução dos gastos ao longo do tempo
- 🍕 **Gráfico de Pizza** — Distribuição por categoria
- 📊 **Gráfico de Barras** — Comparativo semanal/mensal
- 💰 **Cards Resumo** — Total gasto na semana, no mês e média diária

Todos os gráficos são renderizados com **Chart.js** e atualizados dinamicamente.

---

## 🗃️ Modelo de Dados

### Entidade `Gasto`

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Identificador único (auto-incremento) |
| `descricao` | `String` | Descrição do gasto |
| `valor` | `BigDecimal` | Valor do gasto |
| `categoria` | `String` | Categoria (ex: ALIMENTAÇÃO, TRANSPORTE) |
| `data` | `LocalDate` | Data do gasto |
| `usuario` | `User` | Usuário dono do gasto (FK) |

### Entidade `User`

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Identificador único (auto-incremento) |
| `nome` | `String` | Nome do usuário |
| `email` | `String` | Email (login) |
| `senha` | `String` | Senha criptografada (BCrypt) |

---

## 🛠️ Possíveis Melhorias Futuras

- [ ] Exportação de relatórios em PDF/Excel
- [ ] Notificações de limites de gastos
- [ ] Filtros avançados por período e categoria
- [x] Modo escuro (dark mode) ✅
- [ ] Deploy com Docker + Docker Compose
- [ ] Integração com APIs de bancos (Open Banking)
- [ ] Aplicativo mobile com React Native
- [x] Validação de email e senha no cadastro ✅
- [x] Maven Wrapper (sem necessidade de instalar Maven) ✅

---

## 🤝 Contribuindo

Contribuições são sempre bem-vindas! Para contribuir:

1. Faça um **fork** do projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/nova-feature`)
3. Faça **commit** das suas alterações (`git commit -m 'feat: adiciona nova feature'`)
4. Faça **push** para a branch (`git push origin feature/nova-feature`)
5. Abra um **Pull Request**

---

## 📝 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

<table>
  <tr>
    <td align="center">
      <strong>João Guilherme de Souza Cordeiro</strong><br/>
      Desenvolvedor Full Stack<br/><br/>
      <a href="https://github.com/seu-usuario">
        <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" />
      </a>
    </td>
  </tr>
</table>

---

<p align="center">
  Feito com ❤️ por <strong>João Guilherme de Souza Cordeiro</strong>
</p>
