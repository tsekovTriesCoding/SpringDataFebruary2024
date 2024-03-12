package org.example.jsonprocessingexercise.domain.dtos.user.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.user.UserWithProductsDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersProductsWrapper {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "user")
    private List<UserWithProductsDto> users;

    public UsersProductsWrapper() {
    }

    public UsersProductsWrapper(List<UserWithProductsDto> users) {
        this.users = users;
        this.count = users.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserWithProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDto> users) {
        this.users = users;
    }
}
