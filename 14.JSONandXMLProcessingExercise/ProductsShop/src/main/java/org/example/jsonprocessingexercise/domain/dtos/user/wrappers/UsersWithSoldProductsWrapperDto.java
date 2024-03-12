package org.example.jsonprocessingexercise.domain.dtos.user.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.user.UsersWithSoldProductsDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsWrapperDto {
    @XmlElement(name = "user")
    private List<UserWithSoldProductsXmlWrapperDto> users;

    public UsersWithSoldProductsWrapperDto() {
    }

    public UsersWithSoldProductsWrapperDto(List<UserWithSoldProductsXmlWrapperDto> users) {
        this.users = users;
    }

    public UsersWithSoldProductsWrapperDto ofUsersWithSoldProducts(List<UsersWithSoldProductsDto> input) {
        this.users = UsersWithSoldProductsDto.toUsersWithSoldProductsDto(input);
        return this;
    }

    public List<UserWithSoldProductsXmlWrapperDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductsXmlWrapperDto> users) {
        this.users = users;
    }
}
