package org.example.jsonprocessingexercise.domain.dtos.user.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.product.wrappers.SoldProductsWrapperDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsXmlWrapperDto {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private SoldProductsWrapperDto soldProducts;

    public UserWithSoldProductsXmlWrapperDto() {
    }

    public UserWithSoldProductsXmlWrapperDto(String firstName, String lastName, SoldProductsWrapperDto soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
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

    public SoldProductsWrapperDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsWrapperDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
