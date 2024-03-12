package org.example.cardealer.services.supplier;

import org.example.cardealer.domain.dtos.supplier.LocalSupplierDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface SupplierService {
    List<LocalSupplierDto> getAllByIsImporterIsFalse() throws IOException, JAXBException;
}
