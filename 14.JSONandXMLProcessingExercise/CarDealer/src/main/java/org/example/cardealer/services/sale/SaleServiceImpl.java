package org.example.cardealer.services.sale;

import org.example.cardealer.domain.dtos.sale.SaleDto;
import org.example.cardealer.domain.dtos.sale.SaleWrapperDto;
import org.example.cardealer.domain.dtos.sale.wrappers.SalesInfoWrapperDto;
import org.example.cardealer.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.cardealer.constants.Paths.SALES_WITH_APPLIED_DISCOUNTS_JSON_PATH;
import static org.example.cardealer.constants.Paths.SALES_WITH_APPLIED_DISCOUNTS_XML_PATH;
import static org.example.cardealer.constants.Utils.*;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<SaleWrapperDto> findAllSales() throws IOException, JAXBException {
        List<SaleWrapperDto> sales = this.saleRepository.findAllSales().orElseThrow(NoSuchElementException::new)
                .stream()
                .map(sale -> MODEL_MAPPER.map(sale, SaleDto.class))
                .map(SaleDto::saleWrapperDto)
                .toList();

        writeJsonOnToFile(sales, SALES_WITH_APPLIED_DISCOUNTS_JSON_PATH);

        SalesInfoWrapperDto salesInfoWrapperDto = new SalesInfoWrapperDto(sales);

        writeXmlOnToFile(salesInfoWrapperDto, SALES_WITH_APPLIED_DISCOUNTS_XML_PATH);

        return sales;
    }
}
