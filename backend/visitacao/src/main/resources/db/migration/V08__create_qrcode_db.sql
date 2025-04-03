CREATE TABLE qrcode (
	id SERIAL PRIMARY KEY,
	dados BYTEA NOT NULL,
	tipo VARCHAR(100) NOT NULL,
	data_geracao TIMESTAMP NOT NULL,
	id_ingresso BIGINT NOT NULL,
	
	CONSTRAINT fk_ingresso FOREIGN KEY (id_ingresso) REFERENCES ingresso(id)
)

