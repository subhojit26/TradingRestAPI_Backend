package com.trading.trading_api.service;

import com.trading.trading_api.dto.AlphaVantageResponse;
import com.trading.trading_api.dto.DailyStockData;
import com.trading.trading_api.dto.StockDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Value("${alphavantage.api.key:Y3S5JIOR9KMAK1ZG}")
    private String apiKey;

    @Value("${alphavantage.api.url:https://www.alphavantage.co/query}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public StockService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public StockDataDto getLatestStockData(String symbol) throws Exception {
        String url = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&outputsize=compact&apikey=%s",
                apiUrl, symbol, apiKey);

        try {
            // Call the Alpha Vantage API
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                throw new RuntimeException("No response from API");
            }

            // Parse the JSON response
            AlphaVantageResponse apiResponse = objectMapper.readValue(response, AlphaVantageResponse.class);

            if (apiResponse.getTimeSeries() == null || apiResponse.getTimeSeries().isEmpty()) {
                throw new RuntimeException("No time series data found for symbol: " + symbol);
            }

            // Get the latest date (most recent trading day)
            String latestDate = apiResponse.getTimeSeries().keySet().stream()
                    .max(String::compareTo)
                    .orElseThrow(() -> new RuntimeException("No data available"));

            DailyStockData latestDayData = apiResponse.getTimeSeries().get(latestDate);

            // Create response with latest data
            StockDataDto stockResponse = new StockDataDto();
            stockResponse.setSymbol(apiResponse.getMetaData().getSymbol());
            stockResponse.setDate(latestDate);
            stockResponse.setOpen(Double.parseDouble(latestDayData.getOpen()));
            stockResponse.setHigh(Double.parseDouble(latestDayData.getHigh()));
            stockResponse.setLow(Double.parseDouble(latestDayData.getLow()));
            stockResponse.setClose(Double.parseDouble(latestDayData.getClose()));
            stockResponse.setVolume(Long.parseLong(latestDayData.getVolume()));
            stockResponse.setLastRefreshed(apiResponse.getMetaData().getLastRefreshed());

            return stockResponse;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching stock data: " + e.getMessage(), e);
        }
    }
}