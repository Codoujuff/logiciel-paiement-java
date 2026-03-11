package com.company.payment.repository;

import com.company.payment.entity.ModePaiement;
import com.company.payment.entity.Payment;
import com.company.payment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByDatePaiementBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findByModePaiement(ModePaiement mode);
    List<Payment> findByUtilisateurCreateur(User u);

    @Query("select sum(p.montant) from Payment p where p.datePaiement between :start and :end")
    BigDecimal sumMontantBetween(LocalDateTime start, LocalDateTime end);
}
