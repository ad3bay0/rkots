package com.ad3bay0.rkots.iex.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuoteDto {

    private String symbol;
    private String companyName;
    private String primaryExchange;
    private String calculationPrice;
    private int latestPrice;
    private double previousClose;
    private String latestSource;
    
}