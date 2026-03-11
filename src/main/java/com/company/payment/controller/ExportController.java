package com.company.payment.controller;

import com.company.payment.entity.Client;
import com.company.payment.entity.Payment;
import com.company.payment.service.ClientService;
import com.company.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/export")
public class ExportController {

    private final PaymentService paymentService;
    private final ClientService clientService;

    public ExportController(PaymentService paymentService, ClientService clientService) {
        this.paymentService = paymentService;
        this.clientService = clientService;
    }

    @GetMapping("/csv/payments")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    public void exportPaymentsToCsv(HttpServletResponse response) throws IOException {
        List<Payment> payments = paymentService.findAllPayments();
        
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"payments_" + 
            java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv\"");
        
        try (PrintWriter writer = response.getWriter()) {
            // En-tête CSV
            writer.println("ID,ID Client,Nom Client,Prénom Client,Email Client,Montant,Mode Paiement,Référence Transaction,Date Paiement,Statut,Date Création");
            
            // Données
            for (Payment payment : payments) {
                writer.println(String.format("%d,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s",
                    payment.getId(),
                    payment.getClient() != null ? payment.getClient().getId() : "",
                    payment.getClient() != null ? escapeCsv(payment.getClient().getNom()) : "",
                    payment.getClient() != null ? escapeCsv(payment.getClient().getPrenom()) : "",
                    payment.getClient() != null ? escapeCsv(payment.getClient().getEmail()) : "",
                    payment.getMontant(),
                    payment.getModePaiement(),
                    escapeCsv(payment.getReferenceTransaction()),
                    payment.getDatePaiement() != null ? payment.getDatePaiement().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "",
                    payment.getStatut(),
                    payment.getDateCreation() != null ? payment.getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : ""
                ));
            }
        }
    }

    @GetMapping("/csv/clients")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    public void exportClientsToCsv(HttpServletResponse response) throws IOException {
        List<Client> clients = clientService.findAllClients();
        
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"clients_" + 
            java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv\"");
        
        try (PrintWriter writer = response.getWriter()) {
            // En-tête CSV
            writer.println("ID,Nom,Prénom,Email,Téléphone,Adresse,Code Postal,Ville,Salaire,Entreprise");
            
            // Données
            for (Client client : clients) {
                writer.println(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%.2f,%s",
                    client.getId(),
                    escapeCsv(client.getNom()),
                    escapeCsv(client.getPrenom()),
                    escapeCsv(client.getEmail()),
                    escapeCsv(client.getTelephone()),
                    escapeCsv(client.getAdresse()),
                    escapeCsv(client.getCodePostal()),
                    escapeCsv(client.getVille()),
                    client.getSalaire(),
                    escapeCsv(client.getEntreprise())
                ));
            }
        }
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        // Échapper les guillemets et ajouter des guillemets si la valeur contient une virgule ou un guillemet
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
