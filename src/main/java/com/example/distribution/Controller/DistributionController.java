package com.example.distribution.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.distribution.Entity.TradeAllocation;
import com.example.distribution.Repo.TradeAllocationRepository;

import java.util.Optional;

@RestController
@RequestMapping("/distribution")
public class DistributionController {

    @Autowired
    private TradeAllocationRepository tradeAllocationRepository;

    @GetMapping("/status/{requestId}")
    public Optional<TradeAllocation> getAllocationStatus(@PathVariable String requestId) {
        return tradeAllocationRepository.findById(requestId);
    }
}
