# 🔩 Gestão de Tasks

API para gerenciamento de tarefas (tasks) e projetos, com autenticação e controle de usuários.

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
│   │   └── resources/              # Configurações e application.properties
│   └── test/                       # Testes unitários e de integração
├── Dockerfile
├── docker-compose.yml
├── .env.example
└── README.md
```

---

## 👨‍💻 Autor

Desenvolvido por **Ryan Victor**

---
