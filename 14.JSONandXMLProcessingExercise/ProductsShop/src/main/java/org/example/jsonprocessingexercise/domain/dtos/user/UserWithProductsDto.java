package org.example.jsonprocessingexercise.domain.dtos.user;

import org.example.jsonprocessingexercise.domain.dtos.product.ProductNameAndPriceDto;
import org.example.jsonprocessingexercise.domain.dtos.product.ProductsSoldWithCountDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsDto {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute
    private Integer age;
    @XmlElement(name = "sold-products")
    private ProductsSoldWithCountDto products;

    public UserWithProductsDto() {
    }

    public UserWithProductsDto(String firstName, String lastName, Integer age, ProductsSoldWithCountDto products) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.products = products;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductsSoldWithCountDto getProducts() {
        return products;
    }

    public void setProducts(ProductsSoldWithCountDto products) {
        this.products = products;
    }
}
