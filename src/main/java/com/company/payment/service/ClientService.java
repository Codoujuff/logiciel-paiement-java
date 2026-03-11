package com.company.payment.service;

import com.company.payment.dto.ClientRequest;
import com.company.payment.dto.ClientResponse;
import com.company.payment.entity.Client;

import java.util.List;

public interface ClientService {
    List<ClientResponse> findAll();
    List<Client> findAllClients();
    ClientResponse findById(Long id);
    ClientResponse create(ClientRequest request);
    ClientResponse update(Long id, ClientRequest request);
    void delete(Long id);
}
