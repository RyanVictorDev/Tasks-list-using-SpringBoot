# 🧩 Gestão de Tasks

API para gerenciamento de tarefas (tasks) e projetos, com autenticação e controle de usuários.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Docker & Docker Compose**

---

## ⚙️ Funcionalidades Principais

- 🔐 Autenticação de usuários  
- 👥 Cadastro e gerenciamento de usuários  
- 📁 CRUD completo de projetos  
- ✅ CRUD completo de tarefas (tasks)  
- 🧾 Validações e tratamento de erros personalizados  

---

## 🛠️ Como Rodar o Projeto Localmente (com Docker)

### 1️⃣ Clone o repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2️⃣ Crie o arquivo `.env` na raiz do projeto

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

### 3️⃣ Suba os containers
```bash
docker compose up -d --build
```

### 4️⃣ Acesse a aplicação
- A API estará disponível em:  
  👉 [http://localhost:2030](http://localhost:2030)

---

## 🧠 Estrutura do Projeto

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

## 🧰 Principais Dependências

- **Spring Boot Starter Web**
- **Spring Boot Starter Security**
- **Spring Boot Starter Data JPA**
- **PostgreSQL Driver**
- **Lombok**
- **JWT** (autenticação)
- **Docker Compose**

---

## 🧪 Testes

Para rodar os testes automatizados:
```bash
./mvnw test
```

---

## 👨‍💻 Autor

Desenvolvido por **Ryan Victor**

---