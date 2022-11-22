package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.exceptions.DatabaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.parsers.ProductParser;
import com.devsuperior.dscatalog.repositories.ProductRepository;
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
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest)
                .map(ProductParser::fromProduct);
    }

    @Transactional(readOnly = true)
    public ProductDTO findByID(Long id) {
        return repository.findById(id)
                .map(p -> ProductParser.fromProductAndSet(p, p.getCategories()))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        try {
            return ProductParser.fromProduct(repository.save(ProductParser.fromProductDTO(productDTO, new Product())));
        } catch (EntityNotFoundException e) {
            LOGGER.error("Exception: {}", e.toString());
            throw new ResourceNotFoundException(e.toString());
        }
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            return ProductParser.fromProduct(
                    repository.save(
                            ProductParser.fromProductDTO(productDTO, repository.getOne(id))));
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
