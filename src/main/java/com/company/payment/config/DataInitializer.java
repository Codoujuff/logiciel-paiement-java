package com.company.payment.config;

import com.company.payment.entity.*;
import com.company.payment.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile("default")
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, 
                         ClientRepository clientRepository, PaymentRepository paymentRepository,
                         PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createUsers();
        createClients();
        createPayments();
    }

    private void createRoles() {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("MANAGER");
        createRoleIfNotExists("EMPLOYE");
    }

    private void createUsers() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Administrateur Système")
                    .email("admin@payment.com")
                    .roles(adminRoles)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("manager").isEmpty()) {
            Role managerRole = roleRepository.findByName("MANAGER").orElseThrow();
            Set<Role> managerRoles = new HashSet<>();
            managerRoles.add(managerRole);
            
            User manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager123"))
                    .fullName("Manager Principal")
                    .email("manager@payment.com")
                    .roles(managerRoles)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(manager);
        }

        if (userRepository.findByUsername("employe").isEmpty()) {
            Role employeRole = roleRepository.findByName("EMPLOYE").orElseThrow();
            Set<Role> employeRoles = new HashSet<>();
            employeRoles.add(employeRole);
            
            User employe = User.builder()
                    .username("employe")
                    .password(passwordEncoder.encode("employe123"))
                    .fullName("Employé Standard")
                    .email("employe@payment.com")
                    .roles(employeRoles)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(employe);
        }
    }

    private void createClients() {
        if (clientRepository.count() == 0) {
            // Créer des clients de test avec salaires
            Client client1 = Client.builder()
                    .nom("Dupont")
                    .prenom("Jean")
                    .email("jean.dupont@email.com")
                    .telephone("0612345678")
                    .adresse("123 Rue de la Paix")
                    .codePostal("75001")
                    .ville("Paris")
                    .salaire(new BigDecimal("45000.00"))
                    .entreprise("Tech Solutions")
                    .build();

            Client client2 = Client.builder()
                    .nom("Martin")
                    .prenom("Marie")
                    .email("marie.martin@email.com")
                    .telephone("0623456789")
                    .adresse("45 Avenue des Champs-Élysées")
                    .codePostal("75008")
                    .ville("Paris")
                    .salaire(new BigDecimal("52000.00"))
                    .entreprise("Digital Agency")
                    .build();

            Client client3 = Client.builder()
                    .nom("Bernard")
                    .prenom("Pierre")
                    .email("pierre.bernard@email.com")
                    .telephone("0634567890")
                    .adresse("78 Boulevard Saint-Germain")
                    .codePostal("75006")
                    .ville("Paris")
                    .salaire(new BigDecimal("38000.00"))
                    .entreprise("StartUp Innov")
                    .build();

            Client client4 = Client.builder()
                    .nom("Petit")
                    .prenom("Sophie")
                    .email("sophie.petit@email.com")
                    .telephone("0645678901")
                    .adresse("15 Place de la Concorde")
                    .codePostal("75008")
                    .ville("Paris")
                    .salaire(new BigDecimal("48000.00"))
                    .entreprise("Consulting Group")
                    .build();

            Client client5 = Client.builder()
                    .nom("Robert")
                    .prenom("Lucas")
                    .email("lucas.robert@email.com")
                    .telephone("0656789012")
                    .adresse("200 Rue de Rivoli")
                    .codePostal("75004")
                    .ville("Paris")
                    .salaire(new BigDecimal("41000.00"))
                    .entreprise("Software Factory")
                    .build();

            clientRepository.saveAll(java.util.Arrays.asList(client1, client2, client3, client4, client5));
        }
    }

    private void createPayments() {
        if (paymentRepository.count() == 0) {
            User adminUser = userRepository.findByUsername("admin").orElseThrow();
            java.util.List<Client> clients = clientRepository.findAll();
            
            if (!clients.isEmpty()) {
                Payment payment1 = Payment.builder()
                        .client(clients.get(0))
                        .montant(new BigDecimal("1500.00"))
                        .modePaiement(ModePaiement.CARTE_BANCAIRE)
                        .referenceTransaction("CB_20240310_001")
                        .datePaiement(LocalDateTime.now().minusDays(5))
                        .statut(StatutPaiement.VALIDE)
                        .utilisateurCreateur(adminUser)
                        .dateCreation(LocalDateTime.now().minusDays(5))
                        .build();

                Payment payment2 = Payment.builder()
                        .client(clients.get(1))
                        .montant(new BigDecimal("2500.00"))
                        .modePaiement(ModePaiement.VIREMENT)
                        .referenceTransaction("VIR_20240309_002")
                        .datePaiement(LocalDateTime.now().minusDays(3))
                        .statut(StatutPaiement.EN_ATTENTE)
                        .utilisateurCreateur(adminUser)
                        .dateCreation(LocalDateTime.now().minusDays(3))
                        .build();

                Payment payment3 = Payment.builder()
                        .client(clients.get(2))
                        .montant(new BigDecimal("800.00"))
                        .modePaiement(ModePaiement.CHEQUE)
                        .referenceTransaction("CHQ_20240308_003")
                        .datePaiement(LocalDateTime.now().minusDays(2))
                        .statut(StatutPaiement.VALIDE)
                        .utilisateurCreateur(adminUser)
                        .dateCreation(LocalDateTime.now().minusDays(2))
                        .build();

                Payment payment4 = Payment.builder()
                        .client(clients.get(3))
                        .montant(new BigDecimal("3200.00"))
                        .modePaiement(ModePaiement.CARTE_BANCAIRE)
                        .referenceTransaction("CB_20240307_004")
                        .datePaiement(LocalDateTime.now().minusDays(1))
                        .statut(StatutPaiement.ANNULE)
                        .utilisateurCreateur(adminUser)
                        .dateCreation(LocalDateTime.now().minusDays(1))
                        .build();

                Payment payment5 = Payment.builder()
                        .client(clients.get(4))
                        .montant(new BigDecimal("1800.00"))
                        .modePaiement(ModePaiement.ESPECES)
                        .referenceTransaction("ESP_20240306_005")
                        .datePaiement(LocalDateTime.now())
                        .statut(StatutPaiement.VALIDE)
                        .utilisateurCreateur(adminUser)
                        .dateCreation(LocalDateTime.now())
                        .build();

                paymentRepository.saveAll(java.util.Arrays.asList(payment1, payment2, payment3, payment4, payment5));
            }
        }
    }

    private void createRoleIfNotExists(String name) {
        if (roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(Role.builder().name(name).build());
        }
    }
}
