package org.example.cardealer.domain.dtos.customer.wrappers;

import org.example.cardealer.domain.dtos.customer.CustomerSalesDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersWithBoughtCarsCountWrapperDto {
    @XmlElement(name = "customer")
    private List<CustomerSalesDto> customers;

    public CustomersWithBoughtCarsCountWrapperDto() {
    }

    public CustomersWithBoughtCarsCountWrapperDto(List<CustomerSalesDto> customers) {
        this.customers = customers;
    }

    public List<CustomerSalesDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSalesDto> customers) {
        this.customers = customers;
    }
}
