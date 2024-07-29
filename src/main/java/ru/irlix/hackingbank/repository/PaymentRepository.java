package ru.irlix.hackingbank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.hackingbank.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
