package com.company.payment.service.impl;

import com.company.payment.dto.PaymentRequest;
import com.company.payment.dto.PaymentResponse;
import com.company.payment.entity.Client;
import com.company.payment.entity.Payment;
import com.company.payment.entity.User;
import com.company.payment.repository.ClientRepository;
import com.company.payment.repository.PaymentRepository;
import com.company.payment.repository.UserRepository;
import com.company.payment.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              ClientRepository clientRepository,
                              UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentResponse findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paiement non trouvé: " + id));
        return toResponse(payment);
    }

    @Override
    public PaymentResponse create(PaymentRequest request) {
        Payment payment = new Payment();
        applyRequest(payment, request);
        payment.setDateCreation(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    @Override
    public PaymentResponse update(Long id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paiement non trouvé: " + id));
        applyRequest(payment, request);
        Payment updated = paymentRepository.save(payment);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    private void applyRequest(Payment payment, PaymentRequest req) {
        if (req.getClientId() != null) {
            Client client = clientRepository.findById(req.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("Client non trouvé: " + req.getClientId()));
            payment.setClient(client);
        }
        payment.setMontant(req.getMontant());
        payment.setModePaiement(req.getModePaiement());
        payment.setReferenceTransaction(req.getReferenceTransaction());
        payment.setDatePaiement(req.getDatePaiement());
        payment.setStatut(req.getStatut());
        if (req.getUtilisateurCreateurId() != null) {
            User user = userRepository.findById(req.getUtilisateurCreateurId())
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé: " + req.getUtilisateurCreateurId()));
            payment.setUtilisateurCreateur(user);
        }
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .clientId(p.getClient() != null ? p.getClient().getId() : null)
                .montant(p.getMontant())
                .modePaiement(p.getModePaiement())
                .referenceTransaction(p.getReferenceTransaction())
                .datePaiement(p.getDatePaiement())
                .statut(p.getStatut())
                .utilisateurCreateurId(p.getUtilisateurCreateur() != null ? p.getUtilisateurCreateur().getId() : null)
                .dateCreation(p.getDateCreation())
                .build();
    }
}
