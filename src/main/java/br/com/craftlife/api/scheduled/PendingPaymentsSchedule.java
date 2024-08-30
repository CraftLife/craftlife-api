package br.com.craftlife.api.scheduled;

import br.com.craftlife.api.domain.Payment;
import br.com.craftlife.api.domain.Product;
import br.com.craftlife.api.repository.PaymentRepository;
import br.com.craftlife.api.repository.ProductRepository;
import br.com.craftlife.api.service.EmailService;
import br.com.craftlife.api.service.TebexService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PendingPaymentsSchedule {

    private final Logger logger = LoggerFactory.getLogger(PendingPaymentsSchedule.class);

    private final TebexService tebexService;

    private final ProductRepository productRepository;

    private final PaymentRepository paymentRepository;

    private final EmailService emailService;

    @Value("${application.scheduling.active}")
    private boolean enableScheduling;

    @Scheduled(cron = "0 * * * * *")
    private void execute() {
        if (!enableScheduling) {
            logger.info("Scheduler desativado, cancelando ativação de vips..");
            return;
        }
        List<Payment> pedingPaymentDeliverys = paymentRepository.findByStatus(Payment.Status.APPROVED);

        pedingPaymentDeliverys.forEach(payment -> {

            logger.info("Iniciando ativação do produto {} para {}", payment.getProduct().getName(), payment.getUser().getRealname());
            tebexService.ativarProduto(payment.getProduct(), payment.getUser().getRealname(), payment.getReceivedAmount());
            payment.setStatus(Payment.Status.COMPLETED);
            paymentRepository.save(payment);

            if (Objects.nonNull(payment.getEmail())) {
                try {
                    emailService.sendPurchaseEmail(
                            payment.getEmail(),
                            String.format("%s %s", payment.getFirstname(), payment.getLastname()),
                            payment.getUser().getRealname(),
                            payment.getProduct().getName(),
                            payment.getDateApproved(),
                            payment.getTransactionAmount());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
