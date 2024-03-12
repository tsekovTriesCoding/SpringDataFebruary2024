package org.example.jsonprocessingexercise.domain.dtos.product.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.product.ProductInPriceRangeWithNoBuyerDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInPriceRangeWithNoBuyerWrapperDto {
    @XmlElement(name = "product")
    private List<ProductInPriceRangeWithNoBuyerDto> products;

    public ProductsInPriceRangeWithNoBuyerWrapperDto() {
    }

    public ProductsInPriceRangeWithNoBuyerWrapperDto(List<ProductInPriceRangeWithNoBuyerDto> products) {
        this.products = products;
    }

    public List<ProductInPriceRangeWithNoBuyerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInPriceRangeWithNoBuyerDto> products) {
        this.products = products;
    }
}
