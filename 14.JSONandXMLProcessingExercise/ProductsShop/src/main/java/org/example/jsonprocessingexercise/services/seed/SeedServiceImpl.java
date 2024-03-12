package org.example.jsonprocessingexercise.services.seed;

import org.example.jsonprocessingexercise.domain.dtos.category.CategoryImportDto;
import org.example.jsonprocessingexercise.domain.dtos.category.wrappers.CategoriesImportWrapperDto;
import org.example.jsonprocessingexercise.domain.dtos.product.ProductImportDto;
import org.example.jsonprocessingexercise.domain.dtos.product.wrappers.ProductsImportWrapperDto;
import org.example.jsonprocessingexercise.domain.dtos.user.wrappers.UsersImportWrapperDto;
import org.example.jsonprocessingexercise.domain.entities.Category;
import org.example.jsonprocessingexercise.domain.entities.Product;
import org.example.jsonprocessingexercise.domain.entities.User;
import org.example.jsonprocessingexercise.repositories.CategoryRepository;
import org.example.jsonprocessingexercise.repositories.ProductRepository;
import org.example.jsonprocessingexercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.jsonprocessingexercise.constants.Paths.*;
import static org.example.jsonprocessingexercise.constants.Utils.GSON;
import static org.example.jsonprocessingexercise.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUser() throws IOException, JAXBException {
        if (this.userRepository.count() == 0) {

            // for JSON :
//            FileReader fileReader = new FileReader(USER_JSON_PATH.toFile());

//            List<User> users = Arrays.stream(GSON.fromJson(fileReader, UserImportDto[].class))
//                    .map(userImportDto -> MODEL_MAPPER.map(userImportDto, User.class))
//                    .toList();
//
//            this.userRepository.saveAllAndFlush(users);

            // for XML :
            FileReader fileReader = new FileReader(USER_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(UsersImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            UsersImportWrapperDto usersDto = (UsersImportWrapperDto) unmarshaller.unmarshal(fileReader);

            List<User> users = usersDto.getUsers()
                    .stream()
                    .map(userImportDto -> MODEL_MAPPER.map(userImportDto, User.class))
                    .toList();

            this.userRepository.saveAllAndFlush(users);

            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() == 0) {
            // for JSON :
//            FileReader fileReader = new FileReader(CATEGORY_JSON_PATH.toFile());
//
//            List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDto[].class))
//                    .map(categoryImportDto -> MODEL_MAPPER.map(categoryImportDto, Category.class))
//                    .toList();
            // for XML:
            FileReader fileReader = new FileReader(CATEGORY_XML_PATH.toFile());

            JAXBContext context = JAXBContext.newInstance(CategoriesImportWrapperDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            CategoriesImportWrapperDto categoriesImportWrapperDto = (CategoriesImportWrapperDto) unmarshaller.unmarshal(fileReader);

            List<Category> categories = categoriesImportWrapperDto.getCategories()
                    .stream()
                    .map(categoryImportDto -> MODEL_MAPPER.map(categoryImportDto, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() == 0) {
            // for JSON :
//            FileReader fileReader = new FileReader(PRODUCTS_JSON_PATH.toFile());
//
//            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDto[].class))
//                    .map(productImportDto -> MODEL_MAPPER.map(productImportDto, Product.class))
//                    .map(this::setRandomBuyer)
//                    .map(this::setRandomSeller)
//                    .map(this::setRandomCategories)
//                    .toList();

            //  for XML:
            FileReader fileReader = new FileReader(PRODUCTS_XML_PATH.toFile());
            JAXBContext context = JAXBContext.newInstance(ProductsImportWrapperDto.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            ProductsImportWrapperDto productsImportWrapperDto = (ProductsImportWrapperDto) unmarshaller.unmarshal(fileReader);

            List<Product> products = productsImportWrapperDto.getProducts()
                    .stream()
                    .map(productImportDto -> MODEL_MAPPER.map(productImportDto, Product.class))
                    .map(this::setRandomBuyer)
                    .map(this::setRandomSeller)
                    .map(this::setRandomCategories)
                    .collect(Collectors.toList());

            this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }
    }

    private Product setRandomCategories(Product product) {
        Random random = new Random();

        int categoriesCount = random.nextInt((int) this.categoryRepository.count());

        Set<Category> categories = new HashSet<>();

        IntStream.range(0, categoriesCount)
                .forEach(num -> {
                    Category category = this.categoryRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
                    categories.add(category);
                });

        product.setCategories(categories);

        return product;
    }

    private Product setRandomSeller(Product product) {
        User seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        while (seller.equals(product.getBuyerID())) {
            seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        }

        product.setSellerID(seller);

        return product;
    }

    private Product setRandomBuyer(Product product) {
        final User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        Random random = new Random();
        int low = 1;
        int high = 11;
        int result = random.nextInt(high - low) + low;

        if (result > 8) {
            product.setBuyerID(null);
        } else {
            product.setBuyerID(buyer);
        }

        return product;
    }
}
