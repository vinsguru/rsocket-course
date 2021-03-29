package com.vinsguru.tradingservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class UserStock {

    @Id
    private String id;
    private String userId;
    private String stockSymbol;
    private Integer quantity;

}
