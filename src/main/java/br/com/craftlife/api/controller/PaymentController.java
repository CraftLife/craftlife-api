package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.PaymentResponse;
import br.com.craftlife.api.service.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping( "payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public PaymentResponse createCheckout(@PathVariable Long paymentId) throws MPException, MPApiException, NoSuchElementException {
        return paymentService.consultPayment(paymentId);

    }
}
