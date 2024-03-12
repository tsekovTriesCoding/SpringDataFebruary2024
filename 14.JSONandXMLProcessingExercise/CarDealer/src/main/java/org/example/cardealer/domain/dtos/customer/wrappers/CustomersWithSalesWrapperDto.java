package org.example.cardealer.domain.dtos.customer.wrappers;

import org.example.cardealer.domain.dtos.customer.CustomerWithSalesInfoDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersWithSalesWrapperDto {
    @XmlElement(name = "customer")
    private List<CustomerWithSalesInfoDto> customers;

    public CustomersWithSalesWrapperDto() {
    }

    public CustomersWithSalesWrapperDto(List<CustomerWithSalesInfoDto> customers) {
        this.customers = customers;
    }

    public List<CustomerWithSalesInfoDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerWithSalesInfoDto> customers) {
        this.customers = customers;
    }
}
