CREATE TABLE ingresso (
	id SERIAL PRIMARY KEY,
	status VARCHAR(20) DEFAULT 'pendente' CHECK(status IN('ATIVO', 'PENDENTE', 'CANCELADO', 'VALIDADO', 'EXPIRADO')),
	id_agenda BIGINT NOT NULL,
	nome_completo VARCHAR(255) NOT NULL,
	celular VARCHAR(14) NOT NULL,
	data_nascimento DATE NOT NULL,
	nome_responsavel VARCHAR(255),
	id_usuario BIGINT NOT NULL,
	id_pagamento BIGINT NOT NULL,
	
	CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	CONSTRAINT fk_agenda FOREIGN KEY (id_agenda) REFERENCES agenda(id),
	CONSTRAINT fk_pagamento FOREIGN KEY (id_pagamento) REFERENCES pagamento(id)
)

