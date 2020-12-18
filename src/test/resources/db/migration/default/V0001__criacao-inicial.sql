CREATE TABLE specie (
  id_specie Char(36) COMMENT 'Chave Primaria da Especie' NOT NULL,
  name varchar(100) COMMENT 'Nome da Especie' NOT NULL,
  classification varchar(100) COMMENT 'Classificacao da Especie' NOT NULL,
  language varchar(50) COMMENT 'Linguagem da Especie' NOT NULL,
  created DATETIME COMMENT 'Data de criação da Especie' NOT NULL,
  edited DATETIME COMMENT 'Data de ultima edição da Especie',
  PRIMARY KEY (id_specie)
) ENGINE=InnoDB COMMENT='Cadastro de Especies da Saga';

create table person (
   id_person Char(36) COMMENT 'Chave Primaria do personagem' NOT NULL,
   name Varchar(100) COMMENT 'Nome do personagem' NOT NULL,
   birth_year int COMMENT 'Data de nascimento do personagem' NOT NULL,
   created DATETIME COMMENT 'Data de criação do personagem' NOT NULL,
   edited DATETIME COMMENT 'Data de ultima edição do personagem',
   id_specie Char(36) COMMENT 'Chave Primaria da Especie' NOT NULL,
   primary key (id_person),
   KEY PersonFKSpecie (id_specie),
   CONSTRAINT PersonFKSpecie FOREIGN KEY (id_specie) REFERENCES specie (id_specie)
) engine=InnoDB COMMENT='Cadastro de Pessoa da Saga';

CREATE TABLE film (
  id_film Char(36) COMMENT 'Chave Primaria do filme' NOT NULL,
  name varchar(100) COMMENT 'Nome do filme' NOT NULL,
  watched int COMMENT 'Identificação de filme ja assistido (0 - Sim, 1 - Nao)' NOT NULL,
  PRIMARY KEY (id_film)
) ENGINE=InnoDB COMMENT='Cadastro de Filmes da Saga';

CREATE TABLE person_film (
  id_film Char(36) COMMENT 'Chave Primaria do filme' NOT NULL,
  id_person Char(36) COMMENT 'Chave Primaria do personagem' NOT NULL,
  PRIMARY KEY (id_film, id_person),
  KEY PersonFilmFKFilm (id_film),
  KEY PersonFilmFKPerson (id_person),
  CONSTRAINT PersonFilmFKPerson FOREIGN KEY (id_person) REFERENCES person (id_person),
  CONSTRAINT PersonFilmFKFilm FOREIGN KEY (id_film) REFERENCES film (id_film)
) ENGINE=InnoDB COMMENT='Cadastro de Pessoa para os filmes da Saga';

create table usuario (
    id_usuario Char(36) COMMENT 'Chave Primária' NOT NULL,
    email VARCHAR(180) COMMENT 'Email do usuário (Utilizado para recuperação de senha)',
    login VARCHAR(50) COMMENT 'Login do usuário (Utilizado para acessar o sistema)' NOT NULL,
    nome VARCHAR(100) COMMENT 'Nome do usuário',
    senha VARCHAR(180) COMMENT 'Senha do usuário' NOT NULL,
    situacao ENUM('ATIVO', 'INATIVO') COMMENT 'Situação da Inspeção: Ativo ou Inativo' NOT NULL,
    primary key (id_usuario)
) engine=InnoDB;

create table funcao (
   id_funcao Char(36) COMMENT 'Chave Primaria da funcao' NOT NULL,
    nome varchar(100),
    primary key (id_funcao)
) engine=InnoDB;

create table usuarios_funcoes (
   id_usuario Char(36) COMMENT 'Chave Primária do usuario' NOT NULL,
   id_funcao Char(36) COMMENT 'Chave Primaria da funcao' NOT NULL,
   primary key(id_usuario, id_funcao)
) engine=InnoDB;

alter table usuarios_funcoes
   add constraint UsuarioFuncaoFKFuncao
   foreign key (id_funcao)
   references funcao (id_funcao);

alter table usuarios_funcoes
   add constraint UsuarioFuncaoFKUsuario
   foreign key (id_usuario)
   references usuario (id_usuario);

