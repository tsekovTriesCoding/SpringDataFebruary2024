package org.example.jsonprocessingexercise.domain.dtos.user.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.user.UserImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersImportWrapperDto {
    @XmlElement(name = "user")
    private List<UserImportDto> users;

    public UsersImportWrapperDto() {
    }

    public UsersImportWrapperDto(List<UserImportDto> users) {
        this.users = users;
    }

    public List<UserImportDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserImportDto> users) {
        this.users = users;
    }
}
