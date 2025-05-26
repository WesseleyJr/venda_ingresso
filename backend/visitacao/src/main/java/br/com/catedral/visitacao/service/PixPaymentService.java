package br.com.catedral.visitacao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.catedral.visitacao.dto.PixPaymentRequestDTO;
import br.com.catedral.visitacao.dto.PixPaymentResponseDTO;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PixPaymentService {

	@Value("${mercadopago.access.token}")
	private String accessToken;

	private final WebClient webClient;

	public PixPaymentService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://api.mercadopago.com").build();
	}

	public Mono<PixPaymentResponseDTO> createPixPayment(PixPaymentRequestDTO dto, String email, String cpf, Long idPagamento) {
	    Map<String, Object> body = new HashMap<>();
	    body.put("transaction_amount", dto.getUnitPrice());
	    body.put("description", dto.getTitle());
	    body.put("payment_method_id", "pix");

	    Map<String, Object> payer = new HashMap<>();
	    payer.put("email", email); // Email de teste
	    Map<String, String> identification = new HashMap<>();
	    identification.put("type", "CPF");
	    identification.put("number", cpf); // CPF de teste
	    payer.put("identification", identification);

	    body.put("payer", payer);
	    
	    Map<String, Object> metadata = new HashMap<>();
	    metadata.put("idPagamento", idPagamento);
	    body.put("metadata", metadata);
	    
	    String idempotencyKey = UUID.randomUUID().toString();
	    return webClient.post()
	            .uri("/v1/payments")
	            .header("Authorization", "Bearer " + accessToken)
	            .header("X-Idempotency-Key", idempotencyKey)
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(body)
	            .retrieve()
	            .onStatus(HttpStatusCode::isError, clientResponse ->
	                    clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
	                        System.err.println("Erro do Mercado Pago: " + errorBody);
	                        return Mono.error(new RuntimeException("Erro ao criar pagamento PIX: " + errorBody));
	                    })
	            )
	            .bodyToMono(JsonNode.class)
	            .map(json -> {
	                PixPaymentResponseDTO response = new PixPaymentResponseDTO();
	                response.setQrCode(json.get("point_of_interaction").get("transaction_data").get("qr_code").asText());
	                response.setQrCodeBase64(json.get("point_of_interaction").get("transaction_data").get("qr_code_base64").asText());
	                response.setStatus(json.get("status").asText());
	                response.setId(json.get("id").asText());
	                return response;
	            });
	}

}
