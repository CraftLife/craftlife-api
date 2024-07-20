package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.CheckoutRequest;
import br.com.craftlife.api.controller.dto.CheckoutResponse;
import br.com.craftlife.api.service.CheckoutService;
import br.com.craftlife.api.service.MercadopagoService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping( "checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    private final MercadopagoService mercadopagoService;

    @PostMapping("/order")
    public CheckoutResponse createCheckout(@RequestBody CheckoutRequest request) throws MPException, MPApiException, NoSuchElementException {
        return checkoutService.checkoutOrder(request);

    }

    @PostMapping("/mercadopago/confirmation")
    public void receiveNotification(@RequestParam("id") Long id, @RequestParam("topic") String topic, @RequestParam("source_news") String sourceNews) throws MPException, MPApiException, ValidationException {
        mercadopagoService.processNotification(topic, id);
    }
}
