package org.example.cardealer.domain.dtos.customer.wrappers;

import org.example.cardealer.domain.dtos.customer.CustomerImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersImportWrapperDto {
    @XmlElement(name = "customer")
    private List<CustomerImportDto> customers;

    public CustomersImportWrapperDto() {
    }

    public List<CustomerImportDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerImportDto> customers) {
        this.customers = customers;
    }
}
