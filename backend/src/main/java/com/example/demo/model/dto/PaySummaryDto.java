package com.example.demo.model.dto;

public class PaySummaryDto {

    private Double withoutDiscount;

    private Double discount;

    private Double withDiscount;

    public PaySummaryDto(){}

    public Double getWithoutDiscount() {
        return withoutDiscount;
    }

    public void setWithoutDiscount(Double withoutDiscount) {
        this.withoutDiscount = withoutDiscount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getWithDiscount() {
        return withDiscount;
    }

    public void setWithDiscount(Double withDiscount) {
        this.withDiscount = withDiscount;
    }
}
