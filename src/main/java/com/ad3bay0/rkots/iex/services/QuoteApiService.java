package com.ad3bay0.rkots.iex.services;

import java.util.List;

import com.ad3bay0.rkots.iex.response.QuoteDto;

public interface QuoteApiService {

    public QuoteDto getQuoteBySymbol(String symbol);
    public List<QuoteDto> getDefaultQuotes();
    
}