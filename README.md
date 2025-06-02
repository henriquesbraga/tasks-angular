# 📝 Tasks Application

Uma aplicação simples de tarefas com frontend em Angular e backend em Spring Boot, utilizando PostgreSQL via Docker.

Aplicação disponível em: https://tasks.henriquelabs.com.br/

---

## ✅ Requisitos

Para executar este projeto localmente, você precisa ter instalado:

- [Node.js](https://nodejs.org/) (recomendado: versão 18 ou superior)
- [Java JDK](https://adoptium.net/) (versão 21)
- [Docker + Docker Compose](https://docs.docker.com/compose/)
- [Angular CLI](https://angular.io/cli)

---

## 🐘 Banco de Dados (PostgreSQL via Docker)

1. Navegue até a pasta `tasks-backend`:

   ```bash
   cd tasks-backend
   ```

2. Suba o banco de dados PostgreSQL com Docker:

   ```bash
   docker-compose up
   ```

   Isso iniciará uma instância do banco na porta padrão (5432).

---

## ⚙️ Backend (Spring Boot)

1. Ainda na pasta `tasks-backend`, gere o arquivo `.jar` do projeto:

   ```bash
   ./mvnw clean package
   ```

2. Após o build, execute o projeto com:

   ```bash
   java -jar target/tasks-backend-0.0.1-SNAPSHOT.jar
   ```

   O backend estará disponível em: `http://localhost:8080`

> 💡 Não é necessário abrir uma IDE para executar o backend.

---

## 💻 Frontend (Angular)

1. Navegue até a pasta do frontend:

   ```bash
   cd ../tasks-frontend
   ```

2. Instale as dependências do projeto:

   ```bash
   npm install
   # ou
   yarn install
   ```

3. Inicie o servidor de desenvolvimento:

   ```bash
   ng serve
   ```

   A aplicação Angular estará disponível em: [http://localhost:4200](http://localhost:4200)

---

## 📌 Resumo dos Comandos

```bash
# Banco de dados
cd tasks-backend
docker-compose up

# Backend
./mvnw clean package
java -jar target/tasks-backend-0.0.1-SNAPSHOT.jar

# Frontend
cd ../tasks-frontend
npm install
ng serve
```

---


## 🛠 Tecnologias Utilizadas

- Angular 20
- Spring Boot 3
- PostgreSQL
- Docker / Docker Compose
- Maven

