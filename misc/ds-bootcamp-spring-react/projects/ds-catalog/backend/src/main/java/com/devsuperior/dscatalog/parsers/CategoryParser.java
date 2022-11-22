package com.devsuperior.dscatalog.parsers;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;

public class CategoryParser {

    public static Category fromCategoryDTO(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(), categoryDTO.getName());
    }

    public static Category fromCategoryDTO(CategoryDTO categoryDTO, Category category) {
        category.setName(categoryDTO.getName());
        return category;
    }

    public static CategoryDTO fromCategory(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
