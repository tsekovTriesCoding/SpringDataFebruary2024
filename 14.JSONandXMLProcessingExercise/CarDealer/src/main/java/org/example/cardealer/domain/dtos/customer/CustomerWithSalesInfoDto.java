package org.example.cardealer.domain.dtos.customer;

import org.example.cardealer.constants.LocalDateTypeAdapterXml;
import org.example.cardealer.domain.dtos.sale.EmptySaleDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerWithSalesInfoDto {
    @XmlElement
    private String name;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTypeAdapterXml.class)
    private LocalDateTime birthdate;
    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;

    private Set<EmptySaleDto> sales;

    public CustomerWithSalesInfoDto() {
        this.sales = new HashSet<>();
    }

    public CustomerWithSalesInfoDto(String name, LocalDateTime birthdate, Boolean isYoungDriver, Set<EmptySaleDto> sales) {
        this.name = name;
        this.birthdate = birthdate;
        this.isYoungDriver = isYoungDriver;
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<EmptySaleDto> getSales() {
        return sales;
    }

    public void setSales(Set<EmptySaleDto> sales) {
        this.sales = sales;
    }
}
