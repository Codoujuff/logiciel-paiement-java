package com.company.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientRequest {
    @NotBlank(message = "Le nom est requis")
    private String nom;

    private String prenom;

    @NotBlank(message = "Le téléphone est requis")
    private String telephone;

    private String email;

    private String adresse;

    private String codePostal;

    private String ville;

    private BigDecimal salaire;

    private String entreprise;
}
