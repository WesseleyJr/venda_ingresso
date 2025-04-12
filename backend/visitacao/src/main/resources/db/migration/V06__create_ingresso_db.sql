CREATE TABLE ingresso (
	id SERIAL PRIMARY KEY,
	status VARCHAR(20) DEFAULT 'pendente' CHECK(status IN('ATIVO', 'PENDENTE', 'CANCELADO', 'VALIDADO', 'EXPIRADO')),
	id_agenda BIGINT NOT NULL,
	id_cliente BIGINT NOT NULL,
	
	CONSTRAINT fk_agenda FOREIGN KEY (id_agenda) REFERENCES agenda(id),
	CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id)
)

