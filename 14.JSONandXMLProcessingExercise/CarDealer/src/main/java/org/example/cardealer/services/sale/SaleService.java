package org.example.cardealer.services.sale;

import org.example.cardealer.domain.dtos.sale.SaleWrapperDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface SaleService {
    List<SaleWrapperDto> findAllSales() throws IOException, JAXBException;
}
