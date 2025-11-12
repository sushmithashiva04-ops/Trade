package com.example.trade.Service;


import com.example.trade.Repo.TradeRequestRepository;
import com.example.trade.entity.TradeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import jakarta.jms.Topic;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class TradeRequestProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic tradeRequestTopic;

    @Autowired
    private TradeRequestRepository tradeRequestRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public void sendTradeRequest(Map<String, Object> requestJson) throws JsonProcessingException {
        String requestId = UUID.randomUUID().toString();
        TradeRequest tradeRequest = new TradeRequest();
//        tradeRequest.setRequestId(requestId);
        tradeRequest.setCustomerId((String) requestJson.get("customerId"));
        tradeRequest.setTradeType((String) requestJson.get("tradeType"));
        tradeRequest.setSharesRequested((Integer) requestJson.get("sharesRequested"));
        tradeRequest.setStatus("TO_BE_PROCESSED");
        tradeRequest.setCreatedAt(LocalDateTime.now());
        tradeRequest.setTradeDetailsJson(mapper.writeValueAsString(requestJson));

        // Store in DB
        tradeRequestRepository.save(tradeRequest);

         jmsTemplate.convertAndSend(tradeRequestTopic, tradeRequest);
    }
}
