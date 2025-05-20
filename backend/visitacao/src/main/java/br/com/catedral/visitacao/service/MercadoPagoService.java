package br.com.catedral.visitacao.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.dto.PagamentoStatusDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access_token}")
    private String accessToken;
    
    private final PagamentoService pagamentoService;

    public MercadoPagoService(@Lazy PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }
    
    @Value("${mercadopago.webhook.secret}")
    private String webhookSecret;

    public String criarPreferencia(double valor, String descricao, Long idPagamento) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = "{"
                + "\"items\": ["
                + "  {"
                + "    \"title\": \"" + descricao + "\","
                + "    \"quantity\": 1,"
                + "    \"unit_price\": " + valor
                + "  }"
                + "],"
                + "\"external_reference\": \"" + idPagamento + "\""
                + "}";

        Request request = new Request.Builder()
                .url("https://api.mercadopago.com/checkout/preferences")
                .addHeader("Authorization", "Bearer " + accessToken)
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro na criação da preferência: " + response.body().string());
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            return jsonResponse.get("init_point").getAsString();  // URL do checkout
        }
    }

    
    
    public void processarNotificacao(String payload, String signatureHeader) throws IOException {
    	if (!validarAssinatura(payload, signatureHeader)) {
    	    throw new SecurityException("Assinatura inválida");
    	}
        JsonObject json = JsonParser.parseString(payload).getAsJsonObject();
        String paymentIdMp = json.get("data").getAsJsonObject().get("id").getAsString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.mercadopago.com/v1/payments/" + paymentIdMp)
            .addHeader("Authorization", "Bearer " + accessToken)
            .get()
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao buscar detalhes do pagamento: " + response.body().string());
            }

            String responseBody = response.body().string();
            JsonObject paymentDetails = JsonParser.parseString(responseBody).getAsJsonObject();

            String statusMp = paymentDetails.get("status").getAsString();
            String externalReference = paymentDetails.get("external_reference").getAsString();

            String dateApprovedStr = paymentDetails.has("date_approved") && !paymentDetails.get("date_approved").isJsonNull()
                ? paymentDetails.get("date_approved").getAsString()
                : null;

            LocalDateTime dataPagamento = null;
            if (dateApprovedStr != null) {
                dataPagamento = OffsetDateTime.parse(dateApprovedStr).toLocalDateTime();
            }

            String metodoPagamento = paymentDetails.has("payment_method_id") && !paymentDetails.get("payment_method_id").isJsonNull()
                ? paymentDetails.get("payment_method_id").getAsString()
                : "desconhecido";

            StatusPagamentoEnum statusEnum = mapearStatusMercadoPagoParaEnum(statusMp);

            PagamentoStatusDTO pagamentoStatusDTO = new PagamentoStatusDTO(statusEnum, dataPagamento, metodoPagamento);

            pagamentoService.atualizarStatus(Long.valueOf(externalReference), pagamentoStatusDTO);
        }
    }

    private StatusPagamentoEnum mapearStatusMercadoPagoParaEnum(String statusMp) {
        return switch (statusMp.toLowerCase()) {
            case "approved" -> StatusPagamentoEnum.PAGO;
            case "pending" -> StatusPagamentoEnum.PENDENTE;
            case "cancelled" -> StatusPagamentoEnum.CANCELADO;
            default -> StatusPagamentoEnum.PENDENTE;
        };
    }
    

    private boolean validarAssinatura(String payload, String signatureHeader) {
    	
    	
        try {
        	String secret = webhookSecret;
            String providedHash = signatureHeader.replace("sha256=", "").trim();

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String calculatedHash = Base64.getEncoder().encodeToString(hash);

            return providedHash.equals(calculatedHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
}
