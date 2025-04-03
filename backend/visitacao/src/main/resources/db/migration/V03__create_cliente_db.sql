CREATE TABLE cliente (
	id SERIAL PRIMARY KEY,
	nome_completo VARCHAR(255) NOT NULL,
	celular VARCHAR(14) NOT NULL,
	data_nascimento DATE NOT NULL,
	nome_responsavel VARCHAR(255),
	genero VARCHAR(100) NOT NULL,
	id_usuario BIGINT NOT NULL,
	
	CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
)