package ru.irlix.hackingbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.irlix.hackingbank.dto.ClientDTO;
import ru.irlix.hackingbank.service.ClientService;


@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor

public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("clients", clientService.getAll());
        return "clients";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new ClientDTO());
        return "clientAdd";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("client") ClientDTO clientDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "clientAdd";
        }
        clientService.save(clientService.convertToClient(clientDTO));
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        ClientDTO clientDTO = clientService.getById(id);
        if (clientDTO == null) {
            return "redirect:/clients";
        }
        model.addAttribute("client", clientDTO);
        return "clientAdd";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                               @Valid @ModelAttribute("client") ClientDTO clientDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "clientAdd";
        }
        clientDTO.setId(id);
        clientService.save(clientService.convertToClient(clientDTO));
        return "redirect:/clients";
    }

    @GetMapping("/{id}/delete")
    public String delete
            (@PathVariable Long id) {
        clientService.delete(
                clientService.convertToClient(
                        clientService.getById(id)));
        return "redirect:/clients";
    }
}
