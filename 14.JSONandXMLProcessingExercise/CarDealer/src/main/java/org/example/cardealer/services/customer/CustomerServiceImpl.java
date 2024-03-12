package org.example.cardealer.services.customer;

import org.example.cardealer.domain.dtos.customer.CustomerSalesDto;
import org.example.cardealer.domain.dtos.customer.CustomerWithSalesInfoDto;
import org.example.cardealer.domain.dtos.customer.wrappers.CustomersImportWrapperDto;
import org.example.cardealer.domain.dtos.customer.wrappers.CustomersWithBoughtCarsCountWrapperDto;
import org.example.cardealer.domain.dtos.customer.wrappers.CustomersWithSalesWrapperDto;
import org.example.cardealer.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.cardealer.constants.Paths.*;
import static org.example.cardealer.constants.Utils.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerWithSalesInfoDto> getAllOrderByBirthdate() throws IOException, JAXBException {
        List<CustomerWithSalesInfoDto> customers = this.customerRepository.getAllOrderByBirthdate()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(customer -> MODEL_MAPPER.map(customer, CustomerWithSalesInfoDto.class))
                .toList();

//        writeJsonOnToFile(customers, ORDERED_CUSTOMERS_JSON_PATH);

        CustomersWithSalesWrapperDto customersWithSalesWrapperDto = new CustomersWithSalesWrapperDto(customers);
        writeXmlOnToFile(customersWithSalesWrapperDto, ORDERDER_CUSTOMERS_XML_PATH);

        return customers;
    }

    @Override
    public List<CustomerSalesDto> getAllByAtLEastOneCarBought() throws IOException, JAXBException {
        List<CustomerSalesDto> customers = this.customerRepository.getAllByAtLeastOneCarBought()
                .orElseThrow(NoSuchElementException::new);

        writeJsonOnToFile(customers, CUSTOMERS_TOTAL_SALES_JSON_PATH);

        CustomersWithBoughtCarsCountWrapperDto customersWithBoughtCarsCountWrapperDto =
                new CustomersWithBoughtCarsCountWrapperDto(customers);

        writeXmlOnToFile(customersWithBoughtCarsCountWrapperDto, CUSTOMERS_TOTAL_SALES_XML_PATH);

        return customers;
    }
}
