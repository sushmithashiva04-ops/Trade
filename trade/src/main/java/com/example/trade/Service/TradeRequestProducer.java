package com.example.trade.Service;

import com.example.trade.Repo.TradeRequestRepository;
import com.example.trade.entity.TradeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import jakarta.jms.Topic;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TradeRequestProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic tradeRequestTopic;

    @Autowired
    private TradeRequestRepository tradeRequestRepository;

    public void processAndPublishAll(List<TradeRequest> trades) {
        try {
      
            for (TradeRequest trade : trades) {
                trade.setStatus("TO_BE_PROCESSED");
                trade.setCreatedAt(LocalDateTime.now());
            }
            tradeRequestRepository.saveAll(trades);

            String json = objectMapper.writeValueAsString(trades);

            System.out.println("---------------------------------------------------");
            System.out.println("Publishing batch to topic: " + tradeRequestTopic.getTopicName());
            System.out.println("Message payload (trades count): " + trades.size());
            System.out.println("---------------------------------------------------");

            jmsTemplate.convertAndSend(tradeRequestTopic, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
