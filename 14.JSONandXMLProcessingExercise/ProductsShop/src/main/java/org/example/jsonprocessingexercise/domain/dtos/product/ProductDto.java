package org.example.jsonprocessingexercise.domain.dtos.product;

import org.example.jsonprocessingexercise.domain.dtos.category.CategoryDto;
import org.example.jsonprocessingexercise.domain.dtos.user.UserDto;
import org.example.jsonprocessingexercise.domain.entities.User;

import java.math.BigDecimal;
import java.util.Set;

public class ProductDto {
    private String name;
    private BigDecimal price;
    private UserDto buyerID;
    private UserDto sellerID;
    private Set<CategoryDto> categories;

    public ProductDto() {
    }

    public ProductDto(String name, BigDecimal price, UserDto buyerID, UserDto sellerID, Set<CategoryDto> categories) {
        this.name = name;
        this.price = price;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.categories = categories;
    }

    public ProductInPriceRangeWithNoBuyerDto ProductInPriceRangeWithNoBuyerDto() {
        return new ProductInPriceRangeWithNoBuyerDto(this.name, this.price, this.sellerID.getFullName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UserDto  getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(UserDto buyerID) {
        this.buyerID = buyerID;
    }

    public UserDto  getSellerID() {
        return sellerID;
    }

    public void setSellerID(UserDto sellerID) {
        this.sellerID = sellerID;
    }

    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }
}
