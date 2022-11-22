package com.devsuperior.dscliente.apis;

import com.devsuperior.dscliente.dtos.ClientDTO;
import com.devsuperior.dscliente.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clients")
public class ClientAPI {

    private final ClientService service;

    public ClientAPI(ClientService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<ClientDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        return ResponseEntity.ok(
                service.findAllPaged(
                        PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findByID(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByID(id));
    }

    @PostMapping()
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO clientDTO) {
        ClientDTO savedObject = service.save(clientDTO);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedObject.getId())
                        .toUri())
                .body(savedObject);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedObject = service.update(id, clientDTO);
        return ResponseEntity.ok(updatedObject);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
