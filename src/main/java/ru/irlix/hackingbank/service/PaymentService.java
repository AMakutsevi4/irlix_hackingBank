package ru.irlix.hackingbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.irlix.hackingbank.model.Client;
import ru.irlix.hackingbank.model.Payment;
import ru.irlix.hackingbank.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClientService clientService;


    public Iterable<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public  Map<Long, String> getClientsNames(){
        Map<Long, String> clientNames = new HashMap<>();
        /*Заполняем мапу именами клиентов*/
        for (Payment payment : getAll()) {
            if (!clientNames.containsKey(payment.getSender_id())) {
                clientNames.put(payment.getSender_id(), clientService.getById(payment.getSender_id()).getName());
            }
            if (!clientNames.containsKey(payment.getRecipient_id())) {
                clientNames.put(payment.getRecipient_id(), clientService.getById(payment.getRecipient_id()).getName());
            }
        }
        return clientNames;
    }

    public void createPayment(Long sender_Id, Long recipient_Id, Double amount, String message) {
        Client sender = clientService.getById(sender_Id);
        Client recipient = clientService.getById(recipient_Id);

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Отрицательный баланс");
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        clientService.save(sender);
        clientService.save(recipient);

        Payment payment = new Payment();
        payment.setDate_time(LocalDateTime.now());
        payment.setAmount(amount);
        payment.setRecipient_id(sender_Id);
        payment.setSender_id(recipient_Id);
        payment.setMessage(message);

        paymentRepository.save(payment);
    }
}
