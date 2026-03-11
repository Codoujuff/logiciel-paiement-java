package com.company.payment.controller;

import com.company.payment.dto.ClientRequest;
import com.company.payment.dto.ClientResponse;
import com.company.payment.entity.Client;
import com.company.payment.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    public String clientsPage(Model model) {
        return "clients";
    }

    @GetMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public List<ClientResponse> listAll() {
        return clientService.findAll();
    }

    @GetMapping("/api/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public ClientResponse getById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PostMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public ResponseEntity<ClientResponse> create(@RequestBody ClientRequest request) {
        ClientResponse created = clientService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/api/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public ClientResponse update(@PathVariable Long id, @RequestBody ClientRequest request) {
        return clientService.update(id, request);
    }

    @DeleteMapping("/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
