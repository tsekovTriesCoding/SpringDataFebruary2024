package org.example.jsonprocessingexercise.domain.dtos.user;

import org.example.jsonprocessingexercise.domain.dtos.product.SoldProductDto;
import org.example.jsonprocessingexercise.domain.dtos.product.wrappers.SoldProductsWrapperDto;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UserWithSoldProductsXmlWrapperDto;

import java.util.List;

public class UsersWithSoldProductsDto {

    private String firstName;
    private String lastName;
    private List<SoldProductDto> soldProducts;

    public UsersWithSoldProductsDto() {
    }

    public UsersWithSoldProductsDto(String firstName, String lastName, List<SoldProductDto> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public static List<UserWithSoldProductsXmlWrapperDto> toUsersWithSoldProductsDto(List<UsersWithSoldProductsDto> input) {
        return input.stream()
                .map(user ->
                        new UserWithSoldProductsXmlWrapperDto(user.getFirstName(),
                                user.getLastName(),
                        new SoldProductsWrapperDto(user.getSoldProducts())))
                .toList();
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

    public List<SoldProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
