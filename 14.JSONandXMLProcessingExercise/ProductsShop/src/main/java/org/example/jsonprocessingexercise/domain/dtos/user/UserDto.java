package org.example.jsonprocessingexercise.domain.dtos.user;

import org.example.jsonprocessingexercise.domain.dtos.product.ProductDto;
import org.example.jsonprocessingexercise.domain.dtos.product.ProductNameAndPriceDto;
import org.example.jsonprocessingexercise.domain.dtos.product.ProductsSoldWithCountDto;
import org.example.jsonprocessingexercise.domain.entities.Product;
import org.example.jsonprocessingexercise.domain.entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {
    private String firstName;
    private String lastName;
    private Integer age;
    Set<ProductDto> boughtProducts;
    Set<ProductDto> soldProducts;
    Set<User> friends;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, Integer age, Set<ProductDto> boughtProducts, Set<ProductDto> soldProducts, Set<User> friends) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.boughtProducts = boughtProducts;
        this.soldProducts = soldProducts;
        this.friends = friends;
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

    public Set<ProductDto> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(Set<ProductDto> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    public Set<ProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public UserWithProductsDto toUserWithProductsDto() {
        return new UserWithProductsDto(this.firstName, this.lastName, this.age, toProductsSoldWithCountDto());
    }

    public ProductsSoldWithCountDto toProductsSoldWithCountDto() {
        return new ProductsSoldWithCountDto(this.soldProducts.stream()
                .map(this::toProductNameAndPriceDto)
                .collect(Collectors.toList()));
    }

    public ProductNameAndPriceDto toProductNameAndPriceDto(ProductDto productDto) {
        return new ProductNameAndPriceDto(productDto.getName(), productDto.getPrice());
    }
}
