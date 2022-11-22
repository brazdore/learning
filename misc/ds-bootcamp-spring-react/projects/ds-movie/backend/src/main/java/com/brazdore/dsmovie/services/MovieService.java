package com.brazdore.dsmovie.services;

import com.brazdore.dsmovie.dtos.MovieDTO;
import com.brazdore.dsmovie.repositories.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(MovieDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        return repository.findById(id)
                .map(MovieDTO::new)
                .orElse(null);
    }
}
