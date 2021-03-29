package com.vinsguru.tradingservice.util;

import com.vinsguru.tradingservice.dto.*;
import com.vinsguru.tradingservice.entity.UserStock;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static TransactionRequest toTransactionRequest(StockTradeRequest stockTradeRequest, int amount){
        TransactionRequest transactionRequest = new TransactionRequest();
        TransactionType transactionType = TradeType.BUY.equals(stockTradeRequest.getTradeType()) ?
                                                    TransactionType.DEBIT : TransactionType.CREDIT;
        transactionRequest.setType(transactionType);
        transactionRequest.setUserId(stockTradeRequest.getUserId());
        transactionRequest.setAmount(amount);
        return transactionRequest;
    }

    public static StockTradeResponse toTradeResponse(StockTradeRequest request, TradeStatus status, int price){
        StockTradeResponse response = new StockTradeResponse();
        BeanUtils.copyProperties(request, response);
        response.setTradeStatus(status);
        response.setPrice(price);
        return response;
    }

    public static UserStock toUserStock(StockTradeRequest request){
        UserStock stock = new UserStock();
        stock.setUserId(request.getUserId());
        stock.setStockSymbol(request.getStockSymbol());
        stock.setQuantity(0);
        return stock;
    }

    public static UserStockDto toUserStockDto(UserStock userStock){
        UserStockDto dto = new UserStockDto();
        BeanUtils.copyProperties(userStock, dto);
        return dto;
    }

}
