package com.vinsguru.rsocket.dto;

public class RequestDto {

    private int input;

    public RequestDto() {
    }

    public RequestDto(int input) {
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "input=" + input +
                '}';
    }
}
