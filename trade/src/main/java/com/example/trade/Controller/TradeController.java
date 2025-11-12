package com.example.trade.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import com.example.trade.entity.TradeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper; 

    @PostMapping
    public String captureTrades(@RequestBody List<TradeRequest> trades) {
        try {
   
            String json = objectMapper.writeValueAsString(trades);

           
            jmsTemplate.convertAndSend("trade.captured.queue", json);

            System.out.println("📤 Sent " + trades.size() + " trades to queue as JSON");

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send trades!";
        }

        return "All trades submitted for processing!";
    }
}
