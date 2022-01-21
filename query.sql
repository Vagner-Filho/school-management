CREATE DATABASE escola;
USE escola;

CREATE TABLE escola(
	escola_id INT NOT NULL AUTO_INCREMENT,
    escola_nome VARCHAR(255) UNIQUE NOT NULL,
    escola_endereco VARCHAR(255) NOT NULL,
    PRIMARY KEY (escola_id)
);

CREATE TABLE aluno (
	aluno_id INT NOT NULL AUTO_INCREMENT,
    aluno_nome VARCHAR(255) NOT NULL,
    aluno_cpf VARCHAR(11) UNIQUE NOT NULL,
    aluno_matricula VARCHAR(10) UNIQUE NOT NULL,
    aluno_email VARCHAR(255) NOT NULL,
    aluno_escola_id INT,
    PRIMARY KEY (aluno_id),
    FOREIGN KEY (aluno_escola_id) REFERENCES escola(escola_id)
);

INSERT INTO escola (escola_id, escola_nome, escola_endereco)
VALUES (1, 'Escola do Java', 'Rua da Programação, nº 2017');

INSERT INTO escola VALUES (2, 'Plataforma Discent', 'Avenida Java, nº 256');

INSERT INTO aluno (aluno_id, aluno_nome, aluno_cpf, aluno_matricula,
aluno_email, aluno_escola_id)
VALUES (1, 'João Fulano', '99999999999', '999999', 'joao@email.com', 1);

INSERT INTO aluno
VALUES (2, 'Carlos Ciclano', '11111111111', '123456', 'carlos@email.com', 1);

INSERT INTO aluno
VALUES (3, 'Maria de Tal', '98765432199', '654321', 'maria@email.com', 1);

INSERT INTO aluno
VALUES (4, 'Vagner Filho', '78945635781', '4457', 'filho@gmail.com', 2);