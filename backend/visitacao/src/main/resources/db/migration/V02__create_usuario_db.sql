CREATE TABLE usuario (
	id SERIAL PRIMARY KEY,
	primeiro_nome VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	senha VARCHAR(255) NOT NULL
)