package com.devsuperior.dscliente.services;

import com.devsuperior.dscliente.dtos.ClientDTO;
import com.devsuperior.dscliente.exceptions.DatabaseException;
import com.devsuperior.dscliente.exceptions.ResourceNotFoundException;
import com.devsuperior.dscliente.parsers.ClientParser;
import com.devsuperior.dscliente.repositories.ClientRepository;
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
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(ClientParser::fromClient);
    }

    @Transactional(readOnly = true)
    public ClientDTO findByID(Long id) {
        return repository.findById(id)
                .map(ClientParser::fromClient)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public ClientDTO save(ClientDTO clientDTO) {
        return ClientParser.fromClient(repository.save(ClientParser.fromClientDTO(clientDTO)));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        try {
            return ClientParser.fromClient(
                    repository.save(
                            ClientParser.fromClientDTO(clientDTO, repository.getOne(id))));
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
            throw new DatabaseException("delete");
        }
    }
}
