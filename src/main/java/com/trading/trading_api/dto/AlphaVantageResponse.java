package com.trading.trading_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class AlphaVantageResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, DailyStockData> timeSeries;

    // Getters and Setters
    public MetaData getMetaData() { return metaData; }
    public void setMetaData(MetaData metaData) { this.metaData = metaData; }

    public Map<String, DailyStockData> getTimeSeries() { return timeSeries; }
    public void setTimeSeries(Map<String, DailyStockData> timeSeries) { this.timeSeries = timeSeries; }
}
