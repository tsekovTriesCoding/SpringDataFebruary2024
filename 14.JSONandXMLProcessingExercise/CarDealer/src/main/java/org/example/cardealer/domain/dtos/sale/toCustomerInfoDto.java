package org.example.cardealer.domain.dtos.sale;

import java.math.BigDecimal;

public class toCustomerInfoDto {
    private String customerName;
    private Double discount;
    private BigDecimal price;
    private BigDecimal priceWithoutDiscount;

    public toCustomerInfoDto(String customerName, Double discount, BigDecimal price, BigDecimal priceWithoutDiscount) {
        this.customerName = customerName;
        this.discount = discount;
        this.price = price;
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(BigDecimal priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }
}
