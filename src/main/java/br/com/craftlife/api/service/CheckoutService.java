package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.CheckoutRequest;
import br.com.craftlife.api.controller.dto.CheckoutResponse;
import br.com.craftlife.api.domain.Product;
import br.com.craftlife.api.repository.ProductRepository;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductRepository productRepository;

    private final MercadopagoService mercadopagoService;
    public CheckoutResponse checkoutOrder(CheckoutRequest checkoutRequest) throws MPException, MPApiException {
        Product product = productRepository.findById(checkoutRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not fouund!"));

        Preference preference =  mercadopagoService.createPreference(product, checkoutRequest);

        return CheckoutResponse.builder().mercadopagoPreferenceId(preference.getId()).build();
    }
}
