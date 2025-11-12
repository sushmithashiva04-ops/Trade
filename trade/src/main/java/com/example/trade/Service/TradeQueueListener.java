package com.example.trade.Service;

import com.example.trade.entity.TradeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeQueueListener {

    @Autowired
    private TradeRequestProducer tradeRequestProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @JmsListener(destination = "trade.captured.queue")
    public void onTradeReceived(String tradeJson) {
        try {
            List<TradeRequest> trades = new ArrayList<>();

       
            if (tradeJson.trim().startsWith("[")) {
                trades = objectMapper.readValue(tradeJson, new TypeReference<List<TradeRequest>>() {});
            } else {
              
                TradeRequest singleTrade = objectMapper.readValue(tradeJson, TradeRequest.class);
                trades.add(singleTrade);
            }

            System.out.println("📩 Received " + trades.size() + " trade(s) from queue");

            tradeRequestProducer.processAndPublishAll(trades);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
