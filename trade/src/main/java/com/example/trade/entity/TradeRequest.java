package com.example.trade.entity;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TradeRequest implements Serializable {
	   private static final long serialVersionUID = 1L; 

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID requestId;

    private String customerId;
    private int sharesRequested;
    private String tradeType; // BUY / SELL
    private String status;    // TO_BE_PROCESSED, ALLOCATED, PARTIALLY_ALLOCATED
    private LocalDateTime createdAt;

    @Lob
    private String tradeDetailsJson;

    // Getters and Setters
    public UUID getRequestId() { return requestId; }
    public void setRequestId(UUID requestId) { this.requestId = requestId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public int getSharesRequested() { return sharesRequested; }
    public void setSharesRequested(int sharesRequested) { this.sharesRequested = sharesRequested; }
    public String getTradeType() { return tradeType; }
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getTradeDetailsJson() { return tradeDetailsJson; }
    public void setTradeDetailsJson(String tradeDetailsJson) { this.tradeDetailsJson = tradeDetailsJson; }
}
