package com.trading.trading_api.model;

public enum TradeType {
    BUY("Buy"),
    SELL("Sell");

    private final String displayName;

    TradeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
