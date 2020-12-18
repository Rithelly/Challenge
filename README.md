# Desafio Técnico AS2 GROUP

Seja Bem-vindo(a) ao Desafio Técnico da AS2 GROUP!

# Instruções

Olá \o/, para participar do desafio técnico da AS2 GROUP é bem simples: faça um [fork](https://confluence.atlassian.com/bitbucket/forking-a-repository-221449527.html) deste repositório e após concluir o desafio nos envie o link do seu projeto para avaliarmos com carinho.

# Desafio

Desafio Star Wars

Queremos desenvolver uma **API REST** que contenha os dados dos personagens do filme Star Wars. Para cada personagem armazene o nome, espécie e os filmes que participou. Para cada filme armazene o nome e os personagens.

# Requisitos:

- Crie uma API que faça o **cadastro** de um novo filme e vincule com algum personagem
- Crie uma API que busque o filme pelo nome do personagem. Caso as informações não estejam cadastradas, utilize a API pública do Star Wars (https://swapi.dev/) para encontrar e persista no banco de dados da aplicação
- O seu modelo pode refletir a resposta da API, mas não esqueça de normalizar suas tabelas :D
- Crie uma API para listar os personagens de um filme utilizando subrecursos, por exemplo: `/films/1/person`
- Crie uma API que **atualize** um filme e marque quais filmes já foram assistidos
- Crie uma API que **delete** um filme
- Retorne erro HTTP, caso na API de cadastro de personagem a espécie não seja encontrada na base da aplicação ou na API pública
- Crie uma API que **cadastre** um usuário

# Referências
- https://swapi.dev/
- https://swapi.dev/documentation
- https://swapi.dev/documentation#person

# Requisitos Técnicos
- Aplicação rodando... :D 
- Java 8+
- Spring boot
- Maven
- Qualquer banco de dados \o/ (se for um banco bem diferentão, por favor, use docker :p)
- Testes Unitários :D
- Testes Integrados :/
- Uso correto de HTTP Status
- Migração de banco de dados

# Considerações
Crie um arquivo .md com a explicação de como executar o seu código e também as suas considerações finais do que foi entregue e o que pode ser melhorado.
Fique à vontade para complementar com algo mais que deseje considerar na sua avaliação, se assim desejar.

# Diferenciais
- Utilização de docker
- Testes de aceitação (UAT) utilizando Gherkins
- Integração com front-end
- Integração Contínua (CI) 
- Utilização de mensageria (RabbitMQ, ActiveMQ, Kafka)
- Documentação da API usando Swagger
- Tratamento de exceções
- JWT

# Finalmentes
Agradecemos pela disposição em topar fazer parte deste desafio, e desejamos muito boa sorte na execução de seu desafio. Esperamos que em breve possamos nos encontrar e formalmente convidá-lo a fazer parte da nossa família!

# Sobre a AS2 GROUP

A AS2 GROUP é uma companhia de tecnologia especializada na implementação de soluções para os mais diversos mercados verticais da indústria. Composta pelos segmentos de Automation Solutions e Application Services, é responsável por projetos e soluções de Engenharia de Automação e Engenharia de Software em grandes clientes com abrangência global. 