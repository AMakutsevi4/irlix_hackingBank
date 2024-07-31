package ru.irlix.hackingbank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.hackingbank.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
