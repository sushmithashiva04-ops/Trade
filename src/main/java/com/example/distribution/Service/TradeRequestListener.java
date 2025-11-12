package com.example.distribution.Service;



import com.example.distribution.Entity.TradeAllocation;
import com.example.distribution.Pojo.TradeRequestDTO;
import com.example.distribution.Repo.TradeAllocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TradeRequestListener {
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private TradeAllocationRepository allocationRepository;
    @Autowired
    private DistributionService distributionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(
    	    destination = "TRADE_REQUEST_TOPIC", 
    	    subscription = "myDurableSub",
    	    containerFactory = "jmsListenerContainerFactory"
    	)
    	public void receiveTradeRequest(String message) throws Exception {
    
    	    List<TradeRequestDTO> trades = objectMapper.readValue(
    	        message,
    	        objectMapper.getTypeFactory().constructCollectionType(List.class, TradeRequestDTO.class)
    	    );

    	    System.out.println("Received " + trades.size() + " trade(s) from topic.");

    	    // Save each trade directly to DB
//    	    for (TradeRequestDTO dto : trades) {
//    	        TradeAllocation allocation = new TradeAllocation();
//    	        allocation.setRequestId(dto.getRequestId());
//    	        allocation.setCustomerId(dto.getCustomerId());
//    	        allocation.setSharesAllocated(dto.getSharesRequested()); // or your allocation logic
//    	        allocation.setStatus("ALLOCATED");
//    	        allocation.setAllocationDate(LocalDateTime.now());
//
//    	        allocationRepository.save(allocation);
//    	        System.out.println("✅ Allocation saved for request: " + dto.getRequestId());
//
//    	        // Process each trade using DistributionService
//    	        distributionService.allocateBatch(trades);
//    	    } distributionService.allocateBatch(trades);
    	    distributionService.allocateBatch(trades);
    	}

}
