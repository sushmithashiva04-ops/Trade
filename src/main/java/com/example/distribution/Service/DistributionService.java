package com.example.distribution.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.distribution.Entity.TradeAllocation;
import com.example.distribution.Pojo.TradeRequestDTO;
import com.example.distribution.Repo.TradeAllocationRepository;

import jakarta.jms.Topic;

@Service
public class DistributionService {

    @Autowired
    private TradeAllocationRepository tradeAllocationRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic tradeAllocationTopic;

    private int totalAvailableShares = 1000; // can be configurable

    @Transactional
    public void allocateBatch(List<TradeRequestDTO> tradeRequests) {

        int totalRequestedShares = tradeRequests.stream()
                .mapToInt(TradeRequestDTO::getSharesRequested)
                .sum();

        for (TradeRequestDTO request : tradeRequests) {

            int allocatedShares = (int) Math.floor(
                    request.getSharesRequested() * ((double) totalAvailableShares / totalRequestedShares)
            );

            if (allocatedShares > totalAvailableShares) {
                allocatedShares = totalAvailableShares;
            }

            totalAvailableShares -= allocatedShares;

            String status = allocatedShares > 0 ? "ALLOCATED" : "AVAILABLE";

            TradeAllocation allocation = new TradeAllocation();
            allocation.setRequestId(request.getRequestId());
            allocation.setCustomerId(request.getCustomerId());
            allocation.setSharesAllocated(allocatedShares);
            allocation.setStatus(status);
            allocation.setAllocationDate(LocalDateTime.now());

            tradeAllocationRepository.save(allocation);

            Map<String, Object> allocationEvent = new HashMap<>();
            allocationEvent.put("requestId", request.getRequestId());
            allocationEvent.put("customerId", request.getCustomerId());
            allocationEvent.put("sharesAllocated", allocatedShares);
            allocationEvent.put("status", status);
            allocationEvent.put("allocationDate", allocation.getAllocationDate().toString());

          
            jmsTemplate.convertAndSend(tradeAllocationTopic, allocationEvent);
        }
    }
}
