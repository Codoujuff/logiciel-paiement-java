package com.company.payment.dto;

import com.company.payment.entity.ModePaiement;
import com.company.payment.entity.StatutPaiement;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRequest {
    private Long clientId;
    private BigDecimal montant;
    private ModePaiement modePaiement;
    private String referenceTransaction;
    private LocalDateTime datePaiement;
    private StatutPaiement statut;
    private Long utilisateurCreateurId;
}
