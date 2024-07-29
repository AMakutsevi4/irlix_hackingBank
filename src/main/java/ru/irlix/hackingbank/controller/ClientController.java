package ru.irlix.hackingbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.irlix.hackingbank.model.Client;
import ru.irlix.hackingbank.service.ClientService;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor

public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String getAll(Model model) {
        Iterable<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "clientAdd";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("client") Client client, BindingResult result) {
        if (result.hasErrors()) {
            return "clientAdd";
        }
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientService.getById(id);
        if (client == null) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client);
        return "clientAdd";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                               @Valid @ModelAttribute("client") Client client, BindingResult result) {
        if (result.hasErrors()) {
            return "clientAdd";
        }
        client.setId(id);
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/delete")
    public String delete
            (@PathVariable Long id) {
        clientService.delete(clientService.getById(id));
        return "redirect:/clients";
    }
}
