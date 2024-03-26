package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.CustomerImportDto;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static exam.constants.Messages.INVALID_CUSTOMER;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_CUSTOMER;
import static exam.constants.Paths.CUSTOMERS_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               TownRepository townRepository,
                               Gson gson,
                               ValidationUtils validationUtils,
                               ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        CustomerImportDto[] customerImportDtos = this.gson.fromJson(this.readCustomersFileContent(), CustomerImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (CustomerImportDto customerImportDto : customerImportDtos) {
            boolean isValid = this.validationUtils.isValid(customerImportDto);

            if (this.customerRepository.findByEmail(customerImportDto.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Customer customerToSave = this.modelMapper.map(customerImportDto, Customer.class);

                Town town = this.townRepository.findByName(customerImportDto.getTown().getName()).get();
                customerToSave.setTown(town);

                this.customerRepository.saveAndFlush(customerToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_CUSTOMER, customerToSave.getFullName(), customerToSave.getEmail()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_CUSTOMER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
