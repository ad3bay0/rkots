package com.ad3bay0.rkots.iex.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class QuoteDto {

    private UUID id;
    private String symbol;
    private String companyName;
    private String primaryExchange;
    private String calculationPrice;
    private BigDecimal latestPrice;
    private BigDecimal previousClose;
    private String latestSource;
    
}