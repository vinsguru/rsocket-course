package com.vinsguru.tradingservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponse {

    private String userId;
    private int amount;
    private TransactionType type;
    private TransactionStatus status;

}
