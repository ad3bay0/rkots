package com.ad3bay0.rkots.iex.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ad3bay0.rkots.exceptions.IEXApiException;
import com.ad3bay0.rkots.iex.response.QuoteDto;
import com.ad3bay0.rkots.util.AppConstants;

import org.hibernate.action.internal.CollectionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuoteApiServiceImpl implements QuoteApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders customHttpHeaders;

    @Value("${iex.key}")
    private String apiKey;

    @Override
    public QuoteDto getQuoteBySymbol(String symbol)  {
        String endPointUrl = generateSymbolStockQuoteUrl(symbol);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPointUrl)
                .queryParam(AppConstants.API_KEY_NAME, apiKey);
        HttpEntity<QuoteDto> entity = new HttpEntity<>(customHttpHeaders);
        ResponseEntity<QuoteDto> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                QuoteDto.class);
        return result.getBody();
    }

    @Override
    public List<QuoteDto> getDefaultQuotes() {

        List<String> defaultList = Arrays.asList("nflx","aapl");

        return defaultList.stream().map(symbol->getQuoteBySymbol(symbol)).collect(Collectors.toList());
    
    }

    String generateSymbolStockQuoteUrl(String symbol){

        return AppConstants.IEX_API_BASE_URL+symbol+"/quote";
    }

}