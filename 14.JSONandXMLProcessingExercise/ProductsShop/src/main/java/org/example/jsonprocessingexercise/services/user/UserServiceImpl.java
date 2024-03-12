package org.example.jsonprocessingexercise.services.user;

import org.example.jsonprocessingexercise.domain.dtos.category.wrappers.CategoriesSummaryWrapperDto;
import org.example.jsonprocessingexercise.domain.dtos.user.UserDto;
import org.example.jsonprocessingexercise.domain.dtos.user.UserWithProductsDto;
import org.example.jsonprocessingexercise.domain.dtos.user.UsersWithSoldProductsDto;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UserWithSoldProductsXmlWrapperDto;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UsersProductsWrapper;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UsersWithProductsWrapperDto;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UsersWithSoldProductsWrapperDto;
import org.example.jsonprocessingexercise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.example.jsonprocessingexercise.constants.Paths.*;
import static org.example.jsonprocessingexercise.constants.Utils.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDto> findAllBySoldProductsBuyerIDIsNotNullOrderByLastNameFirstName() throws IOException, JAXBException {
        List<UsersWithSoldProductsDto> users = this.userRepository
                .findAllBySoldProductsBuyerIDIsNotNullOrderByLastNameFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDto.class))
                .collect(Collectors.toList());

//        List<List<SoldProductDto>> removedNullBuyer = users
//                .stream()
//                .map(userWithSoldProductsDto -> userWithSoldProductsDto.getSoldProducts()
//                        .stream().filter(u -> u.getBuyerLastName() != null).collect(Collectors.toList()))
//                .collect(Collectors.toList());

        final UsersWithSoldProductsWrapperDto usersWithSoldProductsWrapperDto =
                new UsersWithSoldProductsWrapperDto().ofUsersWithSoldProducts(users);

        writeJsonOnToFile(users, USERS_WITH_SOLD_PRODUCTS_JSON_PATH);
        writeXmlOnToFile(usersWithSoldProductsWrapperDto, USERS_WITH_SOLD_PRODUCTS_XML_PATH);

        return users;
    }

    @Override
    public UsersWithProductsWrapperDto usersAndProducts() throws IOException, JAXBException {
        final List<UserWithProductsDto> usersAndProducts = this.userRepository
                .findAllByAtLeastOneSoldProduct()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .filter(user -> user.getSoldProducts().removeIf(product -> product.getBuyerID() == null))
                .map(user -> MODEL_MAPPER.map(user, UserDto.class))
                .map(UserDto::toUserWithProductsDto)
                .collect(Collectors.toList());

        UsersProductsWrapper usersProductsWrapper = new UsersProductsWrapper(usersAndProducts);

        final JAXBContext context = JAXBContext.newInstance(UsersProductsWrapper.class);
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        final UsersWithProductsWrapperDto usersWithProductsWrapperDto = new UsersWithProductsWrapperDto(usersAndProducts);

        writeJsonOnToFile(usersWithProductsWrapperDto, USERS_AND_PRODUCTS_JSON_PATH);
        writeXmlOnToFile(usersProductsWrapper, USERS_AND_PRODUCTS_XML_PATH);

        return usersWithProductsWrapperDto;
    }
}
