package br.com.catedral.visitacao.dto;

import java.util.Map;

public class PixPaymentRequestDTO {
    private String title;
    private int quantity;
    private double unitPrice;
    private Map<String, Object> metadata;

    public PixPaymentRequestDTO() {}

    public PixPaymentRequestDTO(String title, int quantity, double unitPrice, Map<String, Object> metadata) {
        this.title = title;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.metadata = metadata;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    
}
