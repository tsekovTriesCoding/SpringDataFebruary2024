package org.example.jsonprocessingexercise.domain.dtos.user.wrappers;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import org.example.jsonprocessingexercise.domain.dtos.product.wrappers.SoldProductsWrapperDto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsXmlWrapperDto {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private List<SoldProductsWrapperDto> soldProducts;

    public UsersWithSoldProductsXmlWrapperDto() {
    }

    public UsersWithSoldProductsXmlWrapperDto(String firstName, String lastName, List<SoldProductsWrapperDto> soldProducts) {
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

    public List<SoldProductsWrapperDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductsWrapperDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
