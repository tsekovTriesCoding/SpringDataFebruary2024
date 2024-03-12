package org.example.cardealer.domain.dtos.sale.wrappers;

import org.example.cardealer.domain.dtos.sale.SaleWrapperDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesInfoWrapperDto {
    @XmlElement(name = "sale")
    private List<SaleWrapperDto> sales;

    public SalesInfoWrapperDto() {
    }

    public SalesInfoWrapperDto(List<SaleWrapperDto> sales) {
        this.sales = sales;
    }

    public List<SaleWrapperDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleWrapperDto> sales) {
        this.sales = sales;
    }
}
