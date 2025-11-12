package com.example.distribution.Pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeRequestDTO {

    private String requestId;
    private String customerId;
    private String tradeType;
    private int sharesRequested;
    private String status; 

    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// Getters & Setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getTradeType() { return tradeType; }
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }

    public int getSharesRequested() { return sharesRequested; }
    public void setSharesRequested(int sharesRequested) { this.sharesRequested = sharesRequested; }
}
