# 🔩 Task Management

API for managing tasks and projects, with authentication and user control.

---

## 🌐 Choose Your Language

You can read this documentation in:

* 🇧🇷 [**Português (Brasil)**](#-gestão-de-tasks)
* 🇺🇸 [**English**](#-task-management)

---

## 🚀 Technologies Used

* **Java 21**
* **Spring Boot**
* **PostgreSQL**
* **Docker & Docker Compose**

---

## ⚙️ Main Features

* 🔐 User authentication
* 👥 User registration and management
* 📁 Full CRUD for projects
* ✅ Full CRUD for tasks
* 🧾 Custom validations and error handling

---

## 🛠️ Installation & Execution Instructions (with Docker)

### 1. Clone the repository

```bash
git clone https://github.com/your-username/your-repository.git
cd your-repository
```

### 2. Create a `.env` file in the project root

Example content:

```bash
POSTGRES_DB=task_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=admin
SERVER_PORT=2030

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/task_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=admin
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

KEY=MySuperSecretKey1234567890
EXPIRATION_LOGIN=3600000
```

### 3. Build and run the containers

```bash
docker compose up -d --build
```

### 4. Access the application

* API will be available at:
  🔗 [http://localhost:2030](http://localhost:2030)

---

## 💡 Technical Choices

* **Spring Boot**: chosen for productivity, dependency injection, and structured RESTful API support.
* **PostgreSQL**: robust relational database with support for complex data types.
* **Docker**: simplifies environment setup and eliminates local configuration issues.
* **JWT (JSON Web Token)**: ensures secure authentication and session control.

These technologies were selected for **scalability, security, and maintainability**.

---

## 🧠 Use of Artificial Intelligence

During development, **AI (ChatGPT)** was used as an assistant to:

* Improve code structure and best practices in Spring Boot;
* Build optimized queries and dynamic JPA specifications;
* Generate this professional and standardized documentation (README).

---

## 📢 Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/com/back/tasks/...     # Main source code
│   │   └── resources/                  # Configurations and application.properties
│   └── test/                           # Unit and integration tests
├── Dockerfile
├── docker-compose.yml
├── .env.example
└── README.md
```

---

## 👨‍💻 Author

Developed by **Ryan Victor**

---

# 🔩 Gestão de Tasks

API para gerenciamento de tarefas e projetos, com autenticação e controle de usuários.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot**
* **PostgreSQL**
* **Docker & Docker Compose**

---

## ⚙️ Funcionalidades Principais

* 🔐 Autenticação de usuários
* 👥 Cadastro e gerenciamento de usuários
* 📁 CRUD completo de projetos
* ✅ CRUD completo de tarefas (tasks)
* 🧾 Validações e tratamento de erros personalizados

---

## 🛠️ Instruções de Instalação e Execução (com Docker)

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2. Crie o arquivo `.env` na raiz do projeto

Exemplo de conteúdo:

```bash
POSTGRES_DB=task_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=admin
SERVER_PORT=2030

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/task_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=admin
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

KEY=MinhaChaveSuperSecreta1234567890
EXPIRATION_LOGIN=3600000
```

### 3. Suba os containers

```bash
docker compose up -d --build
```

### 4. Acesse a aplicação

* A API estará disponível em:
  🔗 [http://localhost:2030](http://localhost:2030)

---

## 💡 Escolhas Técnicas

* **Spring Boot**: escolhido pela sua produtividade, injeção de dependências e estrutura organizada para APIs RESTful.
* **PostgreSQL**: banco de dados relacional robusto e com excelente suporte a tipos complexos.
* **Docker**: simplifica a execução do ambiente, eliminando problemas de configuração local.
* **JWT (JSON Web Token)**: utilizado para autenticação segura e controle de sessões.

Essas tecnologias foram escolhidas por garantirem **escalabilidade, segurança e manutenção simplificada**.

---

## 🧠 Uso de Inteligência Artificial

Durante o desenvolvimento deste projeto, **foi utilizada IA (ChatGPT)** como assistente de apoio para:

* Refinar a estrutura do código e boas práticas com Spring Boot;
* Criar consultas otimizadas e especificações JPA dinâmicas;
* Gerar esta documentação (README) de forma mais profissional e padronizada.

---

## 📢 Estrutura do Projeto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/back/tasks/...     # Código fonte principal
│   │   └── resources/                  # Configurações e application.properties
│   └── test/                           # Testes unitários e de integração
├── Dockerfile
├── docker-compose.yml
├── .env.example
└── README.md
```

---

## 👨‍💻 Autor

Desenvolvido por **Ryan Victor**

---