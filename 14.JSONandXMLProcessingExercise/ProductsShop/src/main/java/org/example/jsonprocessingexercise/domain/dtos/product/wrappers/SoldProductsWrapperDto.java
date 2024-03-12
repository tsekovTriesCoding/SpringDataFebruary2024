package org.example.jsonprocessingexercise.domain.dtos.product.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.product.SoldProductDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsWrapperDto {
    @XmlElement(name = "product")
    private List<SoldProductDto> products;

    public SoldProductsWrapperDto() {
    }



    public SoldProductsWrapperDto(List<SoldProductDto> products) {
        this.products = products;
    }

    public List<SoldProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<SoldProductDto> products) {
        this.products = products;
    }
}
