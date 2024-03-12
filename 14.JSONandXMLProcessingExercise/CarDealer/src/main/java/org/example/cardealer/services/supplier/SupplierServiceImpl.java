package org.example.cardealer.services.supplier;

import org.example.cardealer.domain.dtos.supplier.LocalSupplierDto;
import org.example.cardealer.domain.dtos.supplier.SupplierDto;
import org.example.cardealer.domain.dtos.supplier.wrappers.LocalSuppliersWrapperDto;
import org.example.cardealer.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.cardealer.constants.Paths.LOCAL_SUPPLIERS_JSON_PATH;
import static org.example.cardealer.constants.Paths.LOCAL_SUPPLIERS_XML_PATH;
import static org.example.cardealer.constants.Utils.*;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<LocalSupplierDto> getAllByIsImporterIsFalse() throws IOException, JAXBException {
        List<LocalSupplierDto> localSuppliers = this.supplierRepository.getAllByIsImporterIsFalse()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(supplier -> MODEL_MAPPER.map(supplier, SupplierDto.class))
                .map(SupplierDto::toLocalSupplierDto)
                .toList();

//        writeJsonOnToFile(localSuppliers, LOCAL_SUPPLIERS_JSON_PATH);

        LocalSuppliersWrapperDto localSuppliersWrapperDto = new LocalSuppliersWrapperDto(localSuppliers);
        writeXmlOnToFile(localSuppliersWrapperDto, LOCAL_SUPPLIERS_XML_PATH);

        return localSuppliers;
    }
}
