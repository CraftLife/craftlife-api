package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.PaymentResponse;
import br.com.craftlife.api.domain.Payment;
import br.com.craftlife.api.domain.Product;
import br.com.craftlife.api.repository.PaymentRepository;
import br.com.craftlife.api.repository.ProductRepository;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MercadopagoService mercadopagoService;

    private final ProductRepository productRepository;

    private final PaymentRepository paymentRepository;

    @SneakyThrows
    public PaymentResponse consultPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);

        com.mercadopago.resources.payment.Payment mercadopagoPayment = mercadopagoService.consultPayment(paymentId);
        MerchantOrder merchantOrder = mercadopagoService.consultMerchantOrder(mercadopagoPayment.getOrder().getId());

        Product product = productRepository.findById(mercadopagoPayment.getAdditionalInfo().getItems().get(0).getId())
                .orElseThrow(() -> new ValidationException("Não foi possível encontrar o produto vinculado ao pagamento!"));

        return PaymentResponse.builder()
                .id(mercadopagoPayment.getId())
                .dateApproved(mercadopagoPayment.getDateApproved().toLocalDateTime())
                .username(merchantOrder.getAdditionalInfo())
                .payerName(String.format("%s %s", mercadopagoPayment.getAdditionalInfo().getPayer().getFirstName(), mercadopagoPayment.getAdditionalInfo().getPayer().getLastName()).trim())
                .payerEmail(mercadopagoPayment.getPayer().getEmail())
                .productName(product.getName())
                .transactionAmount(mercadopagoPayment.getTransactionAmount().doubleValue())
                .amountFee(mercadopagoPayment.getFeeDetails().stream().mapToDouble(fee -> fee.getAmount().doubleValue()).sum())
                .status(mercadopagoPayment.getStatus())
                .processingStarted(Objects.nonNull(payment))
                .delivered(Objects.nonNull(payment) ? payment.getDelivered() : false)
                .build();
    }
}
