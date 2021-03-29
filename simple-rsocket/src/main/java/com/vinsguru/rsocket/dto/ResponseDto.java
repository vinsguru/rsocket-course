package com.vinsguru.rsocket.dto;

public class ResponseDto {

    private int input;
    private int output;

    public ResponseDto() {
    }

    public ResponseDto(int input, int output) {
        this.input = input;
        this.output = output;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "input=" + input +
                ", output=" + output +
                '}';
    }
}
