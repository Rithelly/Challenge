INSERT INTO usuario(id_usuario, email, login, nome, senha, situacao) VALUES ('46416c05-18aa-4d5f-8f1b-8d81679409d', 'teste@teste.com', 'teste', NULL, '$2y$12$6EefJ0kBySgbhLEvafRvs.M8RNGQK9kYoOPPclkS5ud1kDAKN4OdO', 'ATIVO');
INSERT INTO funcao(id_funcao, nome)VALUES('46416c05-18aa-4d5f-8f1b-8d816794283', 'ADM');
INSERT INTO usuarios_funcoes (id_usuario, id_funcao) VALUES ('46416c05-18aa-4d5f-8f1b-8d81679409d', '46416c05-18aa-4d5f-8f1b-8d816794283');
