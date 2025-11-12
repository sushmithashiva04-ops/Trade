package com.example.trade.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import com.example.trade.Repo.TradeRequestRepository;
import com.example.trade.entity.TradeRequest;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private TradeRequestRepository tradeRepository;
    
//    @Autowired
//    private TradeRequest tradeRequest;

    @PostMapping
    public String captureTrades(@RequestBody List<TradeRequest> trades) {
        trades.forEach(trade -> {
            tradeRepository.save(trade);

            // Publish event to ActiveMQ
            jmsTemplate.convertAndSend("trade.captured.queue", trade);
            System.out.println("📩 Published trade event for " + trade.getCustomerId());
        });

        return "Trades captured and published!";
    }
}
