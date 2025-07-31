package com.trading.trading_api.model;

public enum TradeStatus {
    PENDING("Pending"),
    EXECUTED("Executed"),
    FAILED("Failed"),
    CANCELLED("Cancelled");

    private final String displayName;

    TradeStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
