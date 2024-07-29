package ru.irlix.hackingbank.service;

import org.springframework.stereotype.Service;
import ru.irlix.hackingbank.model.Client;
import ru.irlix.hackingbank.repository.ClientRepository;

@Service
public record ClientService(ClientRepository clientRepo) {

    public Iterable<Client> getAll() {
        return clientRepo.findAll();
    }

    public Client getById(Long id) {
        return clientRepo.findById(id).orElse(null);
    }

    public void save(Client client) {
        clientRepo.save(client);
    }

    public void delete(Client client) {
        clientRepo.delete(client);
    }

}
