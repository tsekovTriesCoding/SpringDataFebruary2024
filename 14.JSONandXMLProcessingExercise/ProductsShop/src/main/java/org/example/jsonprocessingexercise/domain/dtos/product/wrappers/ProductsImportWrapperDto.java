package org.example.jsonprocessingexercise.domain.dtos.product.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.product.ProductImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsImportWrapperDto {
    @XmlElement(name = "product")
    private List<ProductImportDto> products;

    public ProductsImportWrapperDto() {
    }

    public ProductsImportWrapperDto(List<ProductImportDto> products) {
        this.products = products;
    }

    public List<ProductImportDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductImportDto> products) {
        this.products = products;
    }
}
