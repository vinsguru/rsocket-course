package com.vinsguru.stockservice.service;

import java.util.concurrent.ThreadLocalRandom;

public class Stock {

    private int price;
    private final String code;
    private final int volatility;

    public Stock(int price, String code, int volatility) {
        this.price = price;
        this.code = code;
        this.volatility = volatility;
    }

    public int getPrice() {
        this.updatePrice();
        return price;
    }

    public String getCode() {
        return code;
    }

    private void updatePrice(){
        int random = ThreadLocalRandom.current().nextInt(-1 * volatility, volatility + 1);
        this.price = random + this.price;
        this.price = Math.max(this.price, 0);
    }



}
