package org.example.jsonprocessingexercise.domain.dtos.user.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.user.UserWithProductsDto;

import java.util.List;

public class UsersWithProductsWrapperDto {
    private Integer usersCount;
    private List<UserWithProductsDto> users;

    public UsersWithProductsWrapperDto(List<UserWithProductsDto> users) {
        this.users = users;
        this.usersCount = users.size();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDto> users) {
        this.users = users;
    }
}
