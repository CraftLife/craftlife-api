package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.CheckoutRequest;
import br.com.craftlife.api.domain.Product;
import br.com.craftlife.api.repository.PaymentRepository;
import br.com.craftlife.api.repository.ProductRepository;
import br.com.craftlife.api.scheduled.PendingPaymentsSchedule;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MercadopagoService {

    private final Logger logger = LoggerFactory.getLogger(PendingPaymentsSchedule.class);

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;

    @Value("${application.mercadopago.access-token}")
    private String mercadopagoAccessToken;

    @Value("${application.api-url}")
    private String apiUrl;



    public Preference createPreference(Product product, CheckoutRequest checkoutRequest) throws MPException, MPApiException, NoSuchElementException {
        MercadoPagoConfig.setAccessToken(mercadopagoAccessToken);

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(PreferenceItemRequest.builder()
                .id(checkoutRequest.getProductId().toString())
                .title(String.format("%s (%s)", product.getName(), checkoutRequest.getIgn()))
                .description(String.format("%s para %s", product.getName(), checkoutRequest.getIgn()))
                .quantity(1)
                .currencyId("BRL")
                .unitPrice(BigDecimal.valueOf(product.getPrice()))
                .build());

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name(checkoutRequest.getName())
                .surname(checkoutRequest.getSurname())
                .email(checkoutRequest.getEmail())
                .identification(IdentificationRequest.builder()
                        .type("CPF")
                        .number(checkoutRequest.getCpf())
                        .build())
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                .expires(true)
                .dateOfExpiration(OffsetDateTime.now().plusDays(1))
                .notificationUrl(String.format("%s/checkout/mercadopago/confirmation?source_news=ipn", apiUrl))
                .paymentMethods(PreferencePaymentMethodsRequest.builder()
                        .excludedPaymentMethods(List.of(PreferencePaymentMethodRequest.builder()
                                .id("bolbradesco")
                                .build()))
                        .excludedPaymentTypes(List.of(PreferencePaymentTypeRequest.builder()
                                .id("bolbradesco")
                                .build()))
                        .build())
                .additionalInfo(checkoutRequest.getIgn())
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }

    @Transactional
    public void processNotification(String topic, Long id) throws MPException, MPApiException, ValidationException {
        logger.info("Processando webhook Mercadopago: {}, id: {}", topic, id);

        MercadoPagoConfig.setAccessToken(mercadopagoAccessToken);
        Payment payment;
        MerchantOrder merchantOrder;

        if (topic.equals("payment")) {
            if (paymentRepository.findById(id).isPresent()) {
                logger.info("Pagamento já cadastrado, processamento cancelado: {}", id);
                throw new ValidationException("Pagamento já cadastrado, processamento cancelado");
            }
            payment = new PaymentClient().get(id);
            merchantOrder = new MerchantOrderClient().get(payment.getOrder().getId());
        } else {
            payment = null;
            merchantOrder = null;
        }

        if (Objects.isNull(merchantOrder)) return;

        BigDecimal paidAmount = merchantOrder.getPayments().stream()
                .filter(merchantOrderPayment -> merchantOrderPayment.getStatus().equals("approved"))
                .map(MerchantOrderPayment::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Product product = productRepository.findById(Long.valueOf(payment.getAdditionalInfo().getItems().get(0).getId())).orElse(null);


        if (paidAmount.compareTo(merchantOrder.getTotalAmount()) >= 0) {
            paymentRepository.save(br.com.craftlife.api.domain.Payment.builder()
                    .id(payment.getId())
                    .username(merchantOrder.getAdditionalInfo())
                    .firstname(payment.getAdditionalInfo().getPayer().getFirstName())
                    .lastname(payment.getAdditionalInfo().getPayer().getLastName())
                    .email(payment.getPayer().getEmail())
                    .product(product)
                    .ipAddress(payment.getAdditionalInfo().getIpAddress())
                    .transactionAmount(payment.getTransactionAmount().doubleValue())
                    .receivedAmount(payment.getTransactionDetails().getNetReceivedAmount().doubleValue())
                    .paymentMethod(payment.getPaymentMethodId())
                    .orderId(payment.getOrder().getId())
                    .status(payment.getStatus())
                    .dateApproved(payment.getDateApproved().toLocalDateTime())
                    .dateCreated(payment.getDateCreated().toLocalDateTime())
                    .lastModified(payment.getDateLastUpdated().toLocalDateTime())
                    .delivered(false)
                    .build());

        }
    }

    public Payment consultPayment(Long id) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(mercadopagoAccessToken);
        return new PaymentClient().get(id);
    }

    public MerchantOrder consultMerchantOrder(Long id) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(mercadopagoAccessToken);
        return new MerchantOrderClient().get(id);
    }
}
