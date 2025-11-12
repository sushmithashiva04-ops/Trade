package com.example.distribution.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TradeAllocation {

    @Id
    private String requestId;
    private String customerId;
    private int sharesAllocated;
    private String status; // ALLOCATED / PARTIALLY_ALLOCATED / TO_BE_PROCESSED
    private LocalDateTime allocationDate;

    // Getters and Setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public int getSharesAllocated() { return sharesAllocated; }
    public void setSharesAllocated(int sharesAllocated) { this.sharesAllocated = sharesAllocated; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getAllocationDate() { return allocationDate; }
    public void setAllocationDate(LocalDateTime allocationDate) { this.allocationDate = allocationDate; }
}
