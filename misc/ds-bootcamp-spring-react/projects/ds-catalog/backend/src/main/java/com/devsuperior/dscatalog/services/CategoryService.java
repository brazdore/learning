package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.exceptions.DatabaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.parsers.CategoryParser;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.utilities.StackWalkerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest)
                .map(CategoryParser::fromCategory);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findByID(Long id) {
        return repository.findById(id)
                .map(CategoryParser::fromCategory)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        return CategoryParser.fromCategory(repository.save(CategoryParser.fromCategoryDTO(categoryDTO)));
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            return CategoryParser.fromCategory(
                    repository.save(
                            CategoryParser.fromCategoryDTO(categoryDTO, repository.getOne(id))));
        } catch (EntityNotFoundException e) {
            LOGGER.error("Exception: {}", e.toString());
            throw new ResourceNotFoundException(e.toString());
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Exception: {}", e.toString());
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Exception: {}", e.toString());
            throw new DatabaseException(StackWalkerUtility.getMethodName());
        }
    }
}
