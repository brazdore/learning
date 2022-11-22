package com.devsuperior.dscliente.parsers;

import com.devsuperior.dscliente.dtos.ClientDTO;
import com.devsuperior.dscliente.entities.Client;

public class ClientParser {

    public static Client fromClientDTO(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getCpf(),
                clientDTO.getIncome(), clientDTO.getBirthDate(), clientDTO.getChildren());
    }

    public static Client fromClientDTO(ClientDTO clientDTO, Client client) {
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setIncome(clientDTO.getIncome());
        client.setChildren(clientDTO.getChildren());
        return client;
    }

    public static ClientDTO fromClient(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getCpf(),
                client.getIncome(), client.getBirthDate(), client.getChildren());
    }
}
