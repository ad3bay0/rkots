package com.ad3bay0.rkots.repository;

import java.util.Optional;
import java.util.UUID;

import com.ad3bay0.rkots.models.Quote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote,UUID> {

    Optional<Quote> findBySymbol(String symbol);

    Boolean existsBySymbol(String symbol);
    
}