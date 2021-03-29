package com.vinsguru.tradingservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserStockDto {

    private String id;
    private String userId;
    private String stockSymbol;
    private Integer quantity;

}
