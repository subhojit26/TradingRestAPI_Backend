package com.trading.trading_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "Stock symbol is required")
    @Size(min = 1, max = 10, message = "Stock symbol must be between 1 and 10 characters")
    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @NotNull(message = "Trade type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false)
    private TradeType tradeType;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TradeStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    // ===========================================
    // CONSTRUCTORS
    // ===========================================

    /**
     * Default constructor - sets initial values
     */
    public Trade() {
        this.createdAt = LocalDateTime.now();
        this.status = TradeStatus.PENDING;
    }

    /**
     * Constructor for creating a new trade
     * @param userId The user ID who placed the order
     * @param symbol The stock symbol (e.g., "AAPL")
     * @param tradeType BUY or SELL
     * @param quantity Number of shares
     */
    public Trade(Long userId, String symbol, TradeType tradeType, Integer quantity) {
        this(); // Call default constructor
        this.userId = userId;
        this.symbol = symbol != null ? symbol.toUpperCase() : null;
        this.tradeType = tradeType;
        this.quantity = quantity;
    }

    // ===========================================
    // GETTERS AND SETTERS
    // ===========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol != null ? symbol.toUpperCase() : null;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // ===========================================
    // BUSINESS METHODS
    // ===========================================

    /**
     * Calculate the total value of this trade (price * quantity)
     * @return Total value, or zero if price is not set
     */
    public BigDecimal getTotalValue() {
        if (price != null && quantity != null) {
            return price.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Mark this trade as successfully executed
     * @param executionPrice The price at which the trade was executed
     */
    public void markAsExecuted(BigDecimal executionPrice) {
        this.price = executionPrice;
        this.status = TradeStatus.EXECUTED;
        this.executedAt = LocalDateTime.now();
        this.errorMessage = null; // Clear any previous error
    }

    /**
     * Mark this trade as failed with an error message
     * @param errorMessage The reason for failure
     */
    public void markAsFailed(String errorMessage) {
        this.status = TradeStatus.FAILED;
        this.errorMessage = errorMessage;
    }

    /**
     * Mark this trade as cancelled
     */
    public void markAsCancelled() {
        this.status = TradeStatus.CANCELLED;
        this.errorMessage = "Cancelled by user";
    }

    /**
     * Check if this trade can be cancelled
     * @return true if trade is in PENDING status
     */
    public boolean canBeCancelled() {
        return this.status == TradeStatus.PENDING;
    }

    // ===========================================
    // toString, equals, hashCode (optional but recommended)
    // ===========================================

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", userId=" + userId +
                ", symbol='" + symbol + '\'' +
                ", tradeType=" + tradeType +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
