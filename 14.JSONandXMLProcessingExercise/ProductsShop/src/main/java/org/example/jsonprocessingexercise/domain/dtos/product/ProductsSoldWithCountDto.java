package org.example.jsonprocessingexercise.domain.dtos.product;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSoldWithCountDto {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "product")
    private List<ProductNameAndPriceDto> products;

    public ProductsSoldWithCountDto() {
    }

    public ProductsSoldWithCountDto(List<ProductNameAndPriceDto> products) {
        this.products = products;
        this.count = products.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductNameAndPriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNameAndPriceDto> products) {
        this.products = products;
    }
}
