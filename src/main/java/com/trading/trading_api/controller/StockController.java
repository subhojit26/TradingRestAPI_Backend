package com.trading.trading_api.controller;

import com.trading.trading_api.dto.StockDataDto;
import com.trading.trading_api.service.StockService;
import com.trading.trading_api.dto.StockDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/latest/{symbol}")
    public ResponseEntity<StockDataDto> getLatestStockData(@PathVariable String symbol) {
        try {
            StockDataDto latestData = stockService.getLatestStockData(symbol.toUpperCase());
            return ResponseEntity.ok(latestData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<StockDataDto> getLatestStockDataByParam(@RequestParam String symbol) {
        try {
            StockDataDto latestData = stockService.getLatestStockData(symbol.toUpperCase());
            return ResponseEntity.ok(latestData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}