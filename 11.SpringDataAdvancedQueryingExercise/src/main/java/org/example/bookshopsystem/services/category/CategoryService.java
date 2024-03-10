package org.example.bookshopsystem.services.category;

import org.example.bookshopsystem.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<Category> categories);
    boolean isDataSeeded();
    Category getRandomCategory();
    Set<Category> getRandomCategories();
}
