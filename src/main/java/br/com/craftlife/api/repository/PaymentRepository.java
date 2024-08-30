package br.com.craftlife.api.repository;

import br.com.craftlife.api.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatus(Payment.Status status);

    List<Payment> findAllByDateApprovedBetweenOrderByDateApprovedDesc(LocalDateTime from, LocalDateTime to);
}
