package com.example.gradleexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeatDto {

    @JsonProperty("row")
    private int row;

    @JsonProperty("column")
    private int column;

    public SeatDto(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
