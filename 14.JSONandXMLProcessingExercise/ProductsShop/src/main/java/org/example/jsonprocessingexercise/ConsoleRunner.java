package org.example.jsonprocessingexercise;

import org.example.jsonprocessingexercise.repositories.CategoryRepository;
import org.example.jsonprocessingexercise.services.category.CategoryService;
import org.example.jsonprocessingexercise.services.product.ProductService;
import org.example.jsonprocessingexercise.services.seed.SeedService;
import org.example.jsonprocessingexercise.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService,
                         ProductService productService,
                         UserService userService,
                         CategoryService categoryRepository) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed the Database
        this.seedService.seedAll();

        // Query 1 – Products in Range
//       this.productService.findAllByPriceBetweenAndBuyerIDIsNullOrderByPrice
//                (BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        // Query 2 – Successfully Sold Products
//        this.userService.findAllBySoldProductsBuyerIDIsNotNullOrderByLastNameFirstName();

        // Query 3 – Categories by Products Count
//       this.categoryRepository.getCategorySummary();

        // Query 4 – Users and Products
//        this.userService.usersAndProducts();

    }
}
