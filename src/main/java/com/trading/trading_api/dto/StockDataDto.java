package com.trading.trading_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDataDto {
    private String symbol;
    private String date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;

    @JsonProperty("last_refreshed")
    private String lastRefreshed;

    // Constructors
    public StockDataDto() {}

    public StockDataDto(String symbol, String date, Double open, Double high,
                           Double low, Double close, Long volume, String lastRefreshed) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.lastRefreshed = lastRefreshed;
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getOpen() { return open; }
    public void setOpen(Double open) { this.open = open; }

    public Double getHigh() { return high; }
    public void setHigh(Double high) { this.high = high; }

    public Double getLow() { return low; }
    public void setLow(Double low) { this.low = low; }

    public Double getClose() { return close; }
    public void setClose(Double close) { this.close = close; }

    public Long getVolume() { return volume; }
    public void setVolume(Long volume) { this.volume = volume; }

    public String getLastRefreshed() { return lastRefreshed; }
    public void setLastRefreshed(String lastRefreshed) { this.lastRefreshed = lastRefreshed; }

    @Override
    public String toString() {
        return String.format("StockDataResponse{symbol='%s', date='%s', open=%.4f, high=%.4f, low=%.4f, close=%.4f, volume=%d}",
                symbol, date, open, high, low, close, volume);
    }
}