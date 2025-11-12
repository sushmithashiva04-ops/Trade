package com.example.trade.Repo;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trade.entity.TradeRequest;

import java.util.List;
@Repository
public interface TradeRequestRepository extends JpaRepository<TradeRequest, String> {
    List<TradeRequest> findByStatus(String status);
}
