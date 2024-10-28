# Pokédex

## Link em produção: https://pokedex-818w.onrender.com

Este é o projeto Pokédex, que permite visualizar informações de Pokémons, desenvolvido usando **Java** com **Spring Boot** no back-end e **React** no front-end. O projeto utiliza bibliotecas como **Bootstrap** para estilização e é configurado para ser executado em contêineres Docker.
O uso de cache para armazenar as informaçoes visa possibilitar uma melhor experiencia para o usuario, alem de permitir buscas mais complexas e maior controle de paginação.

## Tecnologias Utilizadas

- **Java** com **Spring Boot**: Back-end para as APIs de dados de Pokémon.
- **React**: Front-end para a interface do usuário.
- **Bootstrap**: Biblioteca de CSS para estilização responsiva.
- **Axios**: Biblioteca para realizar chamadas HTTP no front-end.
- **SweetAlert2**: Biblioteca para exibir alertas estilizados no front-end.
- **Docker**: Para facilitar a criação de contêineres e a implantação do projeto.

## Requisitos

Antes de começar, você precisará ter instalado em sua máquina:
- **Java 17** ou superior
- **Node.js** (versão 16 ou superior) e **npm**
- **Docker** e **Docker Compose** (opcional, mas recomendado)

## Como Rodar o Projeto Localmente

### 1. Clonar o Repositório

git clone https://github.com/seu-usuario/pokedex.git
cd pokedex
2. Configurar o Back-end (Spring Boot)
Navegue até a pasta do Spring Boot:

cd pokemon
Baixe as dependências e inicie a aplicação:

./mvnw spring-boot:run
A aplicação estará acessível em http://localhost:8080.

3. Configurar o Front-end (React)
Abra uma nova janela de terminal e navegue até a pasta do front-end:

cd pokemon-front/pokedex
Instale as dependências do React:

npm install
Inicie o servidor de desenvolvimento do React:

npm start
O front-end estará acessível em http://localhost:3000.

4. Variáveis de Ambiente
O front-end utiliza uma variável de ambiente para a URL base da API. Crie um arquivo .env na pasta do React (pokemon-front/pokedex) com o seguinte conteúdo:

REACT_APP_API_BASE_URL=http://localhost:8080/api

Como Rodar com Docker
O projeto possui um Dockerfile que unifica o front-end e o back-end em um único contêiner.

Navegue até a pasta raiz do projeto, onde está localizado o Dockerfile:

cd /caminho/para/o/projeto
Construir a imagem Docker:

docker build -t pokedex .
Executar o contêiner Docker:

docker run -d -p 8080:8080 --name pokedex pokedex
Isso irá rodar o projeto na porta 8080, unificando o front-end e o back-end em um único contêiner.

Endpoints da API
A API oferece os seguintes endpoints:

GET /api/pokemon?page=&size: Retorna uma lista de Pokémon.
GET /api/pokemon/{id}: Retorna informações detalhadas de um Pokémon específico.
GET /api/pokemon/filter?name=&type=&habitat=&page=&size=: Filtra Pokémon por nome, tipo e habitat.
Estrutura de Pastas

pokedex/
│
├── pokemon/               # Código do Spring Boot (Back-end)
│   ├── src/               # Código-fonte
│   ├── pom.xml            # Arquivo de configuração Maven
│   └── ...
│
├── pokemon-front/         # Código do React (Front-end)
│   └── pokedex/           # Pasta principal do front-end
│       ├── src/           # Código-fonte
│       ├── public/        # Arquivos estáticos
│       ├── package.json   # Arquivo de configuração npm
│       └── ...
│
├── Dockerfile             # Arquivo para construir a imagem Docker
└── README.md              # Documentação do projeto
Contribuição
Sinta-se à vontade para abrir issues e pull requests para melhorias no projeto!

Licença
Este projeto está sob a licença MIT.

Contato
Nome: Julio Leal
Email: seu-email@exemplo.com
