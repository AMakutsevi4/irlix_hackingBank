package ru.irlix.hackingbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.irlix.hackingbank.model.Payment;
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
        model.addAttribute("payment", new Payment());
        return "paymentAdd";
    }

    @PostMapping
    public String createPayment(@RequestParam Long senderId, @RequestParam Long recipientId,
                                @RequestParam Double amount, @RequestParam String message, Model model) {
        try {
            paymentService.createPayment(senderId, recipientId, amount, message);
            return "redirect:/payments";
        } catch (RuntimeException e) {
            model.addAttribute("clients", clientService.getAll());
            return "paymentAdd";
        }
    }
}
