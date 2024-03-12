package org.example.cardealer.domain.dtos.supplier.wrappers;

import org.example.cardealer.domain.dtos.supplier.LocalSupplierDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSuppliersWrapperDto {
    @XmlElement(name = "supplier")
    private List<LocalSupplierDto> suppliers;

    public LocalSuppliersWrapperDto() {
    }

    public LocalSuppliersWrapperDto(List<LocalSupplierDto> suppliers) {
        this.suppliers = suppliers;
    }

    public List<LocalSupplierDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<LocalSupplierDto> suppliers) {
        this.suppliers = suppliers;
    }
}
