package com.company.payment.service.impl;

import com.company.payment.dto.ClientRequest;
import com.company.payment.dto.ClientResponse;
import com.company.payment.entity.Client;
import com.company.payment.repository.ClientRepository;
import com.company.payment.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public ClientResponse findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé: " + id));
        return toResponse(client);
    }

    @Override
    public ClientResponse create(ClientRequest request) {
        Client client = new Client();
        client.setNom(request.getNom());
        client.setPrenom(request.getPrenom());
        client.setTelephone(request.getTelephone());
        client.setEmail(request.getEmail());
        client.setAdresse(request.getAdresse());
        client.setCodePostal(request.getCodePostal());
        client.setVille(request.getVille());
        client.setSalaire(request.getSalaire());
        client.setEntreprise(request.getEntreprise());
        Client saved = clientRepository.save(client);
        return toResponse(saved);
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé: " + id));
        client.setNom(request.getNom());
        client.setPrenom(request.getPrenom());
        client.setTelephone(request.getTelephone());
        client.setEmail(request.getEmail());
        client.setAdresse(request.getAdresse());
        client.setCodePostal(request.getCodePostal());
        client.setVille(request.getVille());
        client.setSalaire(request.getSalaire());
        client.setEntreprise(request.getEntreprise());
        Client updated = clientRepository.save(client);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private ClientResponse toResponse(Client c) {
        return ClientResponse.builder()
                .id(c.getId())
                .nom(c.getNom())
                .prenom(c.getPrenom())
                .telephone(c.getTelephone())
                .email(c.getEmail())
                .adresse(c.getAdresse())
                .codePostal(c.getCodePostal())
                .ville(c.getVille())
                .salaire(c.getSalaire())
                .entreprise(c.getEntreprise())
                .build();
    }
}
