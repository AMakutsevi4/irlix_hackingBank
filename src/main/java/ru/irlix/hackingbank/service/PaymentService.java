package ru.irlix.hackingbank.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.irlix.hackingbank.dto.ClientDTO;
import ru.irlix.hackingbank.dto.PaymentDTO;
import ru.irlix.hackingbank.model.Payment;
import ru.irlix.hackingbank.repository.ClientRepository;
import ru.irlix.hackingbank.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final ModelMapper modelMapper = new ModelMapper();


    public Iterable<PaymentDTO> getAll() {
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        paymentRepository
                .findAll()
                .forEach(payment -> paymentDTOs
                        .add(modelMapper.map(payment, PaymentDTO.class)));

        return paymentDTOs;
    }


    public Map<Long, String> getClientsNames() {
        Map<Long, String> clientNames = new HashMap<>();
        /*Заполняем мапу именами клиентов*/
        for (PaymentDTO paymentDTO : getAll()) {
            if (!clientNames.containsKey(paymentDTO.getSender_id())) {
                clientNames.put(paymentDTO.getSender_id(), clientService.getById(paymentDTO.getSender_id()).getName());
            }
            if (!clientNames.containsKey(paymentDTO.getRecipient_id())) {
                clientNames.put(paymentDTO.getRecipient_id(), clientService.getById(paymentDTO.getRecipient_id()).getName());
            }
        }
        return clientNames;
    }

    public void createPaymentDTO(Long sender_Id, Long recipient_Id, Double amount, String message) {
        ClientDTO sender = clientService.getById(sender_Id);
        ClientDTO recipient = clientService.getById(recipient_Id);

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Отрицательный баланс");
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        clientService.convertToClient(sender);
        clientService.convertToClient(recipient);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setDate_time(LocalDateTime.now());
        paymentDTO.setAmount(amount);
        paymentDTO.setSender_id(sender_Id);
        paymentDTO.setRecipient_id(recipient_Id);
        paymentDTO.setMessage(message);

        Payment payment = convertToPayment(paymentDTO);

        paymentRepository.save(payment);

        /*Сохраняем баланс клиентов после проведения перевода*/
        clientRepository.save(clientService.convertToClient(sender));
        clientRepository.save(clientService.convertToClient(recipient));



    }

    public Payment convertToPayment(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, Payment.class);
    }

}
