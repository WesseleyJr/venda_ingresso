CREATE TABLE pagamento (
	id SERIAL PRIMARY KEY,
	status VARCHAR(20) DEFAULT 'pendente' CHECK(status IN('ATIVO', 'PENDENTE', 'CANCELADO', 'PAGO')),
	valor DECIMAL NOT NULL,
	metodo_pagamento VARCHAR(50) NOT NULL,
	data_pagamento TIMESTAMP,
	id_ingresso BIGINT NOT NULL,
	
	CONSTRAINT fk_ingresso FOREIGN KEY (id_ingresso) REFERENCES ingresso(id)
)

