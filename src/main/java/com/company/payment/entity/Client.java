package com.company.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String prenom;

    @Column(nullable = false)
    private String telephone;

    private String email;

    private String adresse;

    private String codePostal;

    private String ville;

    private BigDecimal salaire;

    private String entreprise;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Payment> paiements;
}
