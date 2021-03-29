package com.vinsguru.springrsocket.dto;

public class ChartResponseDto {

    private int input;
    private int output;

    public ChartResponseDto() {
    }

    public ChartResponseDto(int input, int output) {
        this.input = input;
        this.output = output;
    }

    public int getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }

    @Override
    public String toString() {
        String graphFormat = getFormat(this.output);
        return String.format(graphFormat, this.input, "X");
    }

    private String getFormat(int value){
        return "%3s|%" + value + "s";
    }


}
