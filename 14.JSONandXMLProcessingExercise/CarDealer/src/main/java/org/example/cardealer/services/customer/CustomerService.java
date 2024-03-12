package org.example.cardealer.services.customer;

import org.example.cardealer.domain.dtos.customer.CustomerSalesDto;
import org.example.cardealer.domain.dtos.customer.CustomerWithSalesInfoDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<CustomerWithSalesInfoDto> getAllOrderByBirthdate() throws IOException, JAXBException;

    List<CustomerSalesDto> getAllByAtLEastOneCarBought() throws IOException, JAXBException;
}
