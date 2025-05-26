package br.com.catedral.visitacao.dto;

public class PixPaymentResponseDTO {
    private String qrCode;
    private String qrCodeBase64;
    private String status;
    private String id;

    public PixPaymentResponseDTO() {}

    public PixPaymentResponseDTO(String qrCode, String qrCodeBase64, String status, String id) {
        this.qrCode = qrCode;
        this.qrCodeBase64 = qrCodeBase64;
        this.status = status;
        this.id = id;
    }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public String getQrCodeBase64() { return qrCodeBase64; }
    public void setQrCodeBase64(String qrCodeBase64) { this.qrCodeBase64 = qrCodeBase64; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
