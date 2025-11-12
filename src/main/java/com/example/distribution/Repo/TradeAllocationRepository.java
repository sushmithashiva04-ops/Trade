package com.example.distribution.Repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.distribution.Entity.TradeAllocation;

public interface TradeAllocationRepository extends JpaRepository<TradeAllocation, String> {
}
