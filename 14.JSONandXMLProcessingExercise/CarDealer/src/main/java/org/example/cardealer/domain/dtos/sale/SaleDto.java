package org.example.cardealer.domain.dtos.sale;

import org.example.cardealer.domain.dtos.car.CarDto;
import org.example.cardealer.domain.dtos.car.CarWithDiscountDto;
import org.example.cardealer.domain.dtos.customer.CustomerDto;
import org.example.cardealer.domain.entities.Part;

import java.math.BigDecimal;

public class SaleDto {
    private Double discountPercentage;
    private CarDto car;
    private CustomerDto customer;

    public SaleDto() {
    }

    public CarWithDiscountDto toCarWithDiscount() {
        return new CarWithDiscountDto(this.car.getMake(), this.car.getModel(), this.car.getTravelledDistance());
    }

    public SaleWrapperDto saleWrapperDto() {
        BigDecimal totalCost = this.car.getParts()
                .stream()
                .map(Part::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        if (customer.getYoungDriver()) {
            this.discountPercentage += 5;
        }

        BigDecimal priceWithoutDiscount = totalCost.multiply(BigDecimal.valueOf(1 - discountPercentage / 100));

        return new SaleWrapperDto(toCarWithDiscount(),this.customer.getName(), this.discountPercentage, totalCost, priceWithoutDiscount);
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}
