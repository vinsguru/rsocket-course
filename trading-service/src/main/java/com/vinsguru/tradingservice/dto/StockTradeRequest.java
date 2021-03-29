package com.vinsguru.tradingservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockTradeRequest {

    private String userId;
    private String stockSymbol;
    private int quantity;
    private TradeType tradeType;

}
