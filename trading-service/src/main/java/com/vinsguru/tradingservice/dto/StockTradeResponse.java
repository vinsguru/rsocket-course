package com.vinsguru.tradingservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockTradeResponse {

    private String userId;
    private String stockSymbol;
    private int quantity;
    private TradeType tradeType;
    private TradeStatus tradeStatus;
    private int price;

}
