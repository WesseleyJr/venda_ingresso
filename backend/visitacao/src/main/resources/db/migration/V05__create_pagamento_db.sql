CREATE TABLE pagamento (
	id SERIAL PRIMARY KEY,
	status VARCHAR(20) DEFAULT 'pendente' CHECK(status IN('ATIVO', 'PENDENTE', 'CANCELADO', 'PAGO')),
	valor DECIMAL NOT NULL,
	metodo_pagamento VARCHAR(50) NOT NULL,
	data_pagamento TIMESTAMP
	
)

