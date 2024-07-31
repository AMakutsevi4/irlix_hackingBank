package ru.irlix.hackingbank.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.irlix.hackingbank.dto.ClientDTO;
import ru.irlix.hackingbank.model.Client;
import ru.irlix.hackingbank.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepo;
    private final ModelMapper modelMapper = new ModelMapper();

    public Iterable<ClientDTO> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        clientRepo.findAll()
                .forEach(client -> clientDTOs.add(modelMapper.map(client, ClientDTO.class)));

        return clientDTOs;
    }

    public ClientDTO getById(Long id) {
        Client client = clientRepo.findById(id).get();
        return modelMapper.map(client, ClientDTO.class);
    }

    public void save(Client client) {
        clientRepo.save(client);
    }

    public void delete(Client client) {
        clientRepo.delete(client);
    }

    public Client convertToClient(ClientDTO clientDTO) {
          return modelMapper.map(clientDTO, Client.class);
    }
}
