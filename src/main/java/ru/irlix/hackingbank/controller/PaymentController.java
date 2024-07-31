package ru.irlix.hackingbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.irlix.hackingbank.dto.PaymentDTO;
import ru.irlix.hackingbank.service.ClientService;
import ru.irlix.hackingbank.service.PaymentService;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ClientService clientService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("payments", paymentService.getAll());
        model.addAttribute("clientNames", paymentService.getClientsNames());
        return "payments";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("clients", clientService.getAll());
        model.addAttribute("payment", new PaymentDTO());
        return "paymentAdd";
    }

    @PostMapping
    public String create(@ModelAttribute("payment") @Valid PaymentDTO paymentDTO,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientService.getAll());
            return "paymentAdd";
        }
        paymentService.createPaymentDTO(
                paymentDTO.getSender_id(),
                paymentDTO.getRecipient_id(),
                paymentDTO.getAmount(),
                paymentDTO.getMessage());
        return "redirect:/payments";
    }
}
