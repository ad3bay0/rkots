package com.ad3bay0.rkots.iex.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ad3bay0.rkots.iex.response.QuoteDto;
import com.ad3bay0.rkots.models.Quote;
import com.ad3bay0.rkots.repository.QuoteRepository;
import com.ad3bay0.rkots.util.AppConstants;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        return saveOrUpateQuoteInDb(result.getBody());
    }

    @Override
    public List<QuoteDto> getDefaultQuotes() {

        List<String> defaultList = Arrays.asList("nflx","aapl");

        return defaultList.stream().map(symbol->getQuoteBySymbol(symbol)).collect(Collectors.toList());
    
    }

    String generateSymbolStockQuoteUrl(String symbol){

        return AppConstants.IEX_API_BASE_URL+symbol+"/quote";
    }


    private QuoteDto saveOrUpateQuoteInDb(QuoteDto dto){


        Quote quote = modelMapper.map(dto, Quote.class);

         Optional<Quote> quoteFromDb = quoteRepository.findBySymbol(quote.getSymbol());

         if(quoteFromDb.isPresent()){
             // update quote details
          quote = quoteRepository.save(quoteFromDb.get().toBuilder()
             .primaryExchange(dto.getPrimaryExchange())
             .calculationPrice(dto.getCalculationPrice())
             .latestPrice(dto.getLatestPrice())
             .latestSource(dto.getLatestSource())
             .previousClose(dto.getPreviousClose())
             .build());

         }else{
             //create new quote
             quote = quoteRepository.save(quote);



         }

         return modelMapper.map(quote, QuoteDto.class);

    }

}