package org.example.springdataadvancedqueryinglab;

import org.example.springdataadvancedqueryinglab.services.ingredient.IngredientService;
import org.example.springdataadvancedqueryinglab.services.shampoo.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        //1. Select Shampoos by Size
//        System.out.println("Size options(small, medium, large)... ENTER size:");
//
//        String size = scanner.nextLine();
//        this.shampooService.findAllBySizeOrderById(size)
//                .forEach(System.out::println);

        // 2. Select Shampoos by Size or Label
//        String size = scanner.nextLine();
//        long labelId = Long.parseLong(scanner.nextLine());
//
//        this.shampooService.findAllBySizeOrLabelIdOrderByPrice(size, labelId)
//                .forEach(System.out::println);


        // 3. Select Shampoos by Price
//        String value = scanner.nextLine();
//        BigDecimal price = new BigDecimal(value);
//
//        this.shampooService.findAllByPriceGreaterThanOrderByPriceDesc(price)
//                .forEach(System.out::println);

        //4. Select Ingredients by Name
//        this.ingredientService.findAllByNameStartingWith("M")
//                .forEach(i -> System.out.println(i.getName()));

        // 5. Select Ingredients by Names
//        List<String> names = List.of("Lavender", "Herbs", "Apple");
//
//        this.ingredientService.findAllByNameIn(names)
//                .forEach(i -> System.out.println(i.getName()));


        // 6. Count Shampoos by Price
//        String value = scanner.nextLine();
//        BigDecimal price = new BigDecimal(value);
//
//        int count = this.shampooService.countAllByPriceLessThan(price);
//        System.out.println(count);

        // JPQL Querying:

        // 7. Select Shampoos by Ingredients
//        List<String> names = List.of("Berry", "Mineral-Collagen");
//        this.shampooService.findAllByIngredientsIn(names)
//                .forEach(s -> System.out.println(s.getBrand()));


        // 8. Select Shampoos by Ingredients Count
//        this.shampooService.findAllByIngredientsCountLessThan(2)
//                .forEach(s -> System.out.println(s.getBrand()));


        // 9. Delete Ingredients by Name
//        this.ingredientService.deleteAllByName("Berry");


        // 10. Update Ingredients by Price
//        this.ingredientService.updateAllIngredientsPrices();

        // 11. Update Ingredients by Names
        List<String> names = List.of("Berry", "Mineral-Collagen");
        this.ingredientService.updateAllIngredientsPricesByNameIn(names);

    }
}
