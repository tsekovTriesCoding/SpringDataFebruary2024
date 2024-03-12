package org.example.cardealer.domain.dtos.sale;

import org.example.cardealer.domain.dtos.car.CarWithDiscountDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWrapperDto {
    @XmlElement
    private CarWithDiscountDto car;
    @XmlElement(name = "customer-name")
    private String customerName;
    private Double discount;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;
    private toCustomerInfoDto carInfoDto;

    public SaleWrapperDto() {
    }

    public SaleWrapperDto(CarWithDiscountDto car, String customerName, Double discount, BigDecimal price, BigDecimal priceWithDiscount) {
        this.car = car;
        this.customerName = customerName;
        this.discount = discount;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    public toCustomerInfoDto getCarInfoDto() {
        return carInfoDto;
    }

    public void setCarInfoDto(toCustomerInfoDto carInfoDto) {
        this.carInfoDto = carInfoDto;
    }

    public CarWithDiscountDto getCar() {
        return car;
    }

    public void setCar(CarWithDiscountDto car) {
        this.car = car;
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

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
