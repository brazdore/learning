package com.devsuperior.dscatalog.parsers;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class ProductParser {

    private static CategoryRepository staticRepository;

    private final CategoryRepository repository;

    public ProductParser(CategoryRepository repository) {
        this.repository = repository;
    }

    public static Product fromProductDTO(ProductDTO productDTO, Product product) {
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
        product.setDate(productDTO.getDate());

        productDTO.getCategories().forEach(categoryDTO -> { // Não devemos salvar/atualizar um Product com/para uma Category inexistente.
            Category one = staticRepository.getOne(categoryDTO.getId());
            product.getCategories().add(one);
        });
        return product;
    }

    public static ProductDTO fromProduct(Product entity) {
        return new ProductDTO(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getPrice(), entity.getImgUrl(), entity.getDate());
    }

    public static ProductDTO fromProductAndSet(Product entity, Set<Category> categories) {
        ProductDTO productDTO = fromProduct(entity);
        categories.forEach(cat ->
                productDTO.getCategories().add(CategoryParser.fromCategory(cat)));
        return productDTO;
    }

    @PostConstruct
    private void init() {
        staticRepository = repository;
    }
}
