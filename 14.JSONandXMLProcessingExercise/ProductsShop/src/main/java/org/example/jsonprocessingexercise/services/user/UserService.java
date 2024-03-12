package org.example.jsonprocessingexercise.services.user;

import org.example.jsonprocessingexercise.domain.dtos.user.UsersWithSoldProductsDto;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UsersWithProductsWrapperDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UsersWithSoldProductsDto> findAllBySoldProductsBuyerIDIsNotNullOrderByLastNameFirstName() throws IOException, JAXBException;

    UsersWithProductsWrapperDto usersAndProducts() throws IOException, JAXBException;
}
