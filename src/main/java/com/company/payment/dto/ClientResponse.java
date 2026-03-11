package com.company.payment.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClientResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;
    private String codePostal;
    private String ville;
    private BigDecimal salaire;
    private String entreprise;
}
