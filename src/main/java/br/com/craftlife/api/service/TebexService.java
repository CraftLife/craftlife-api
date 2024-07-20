package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.TebexPaymentRequest;
import br.com.craftlife.api.controller.dto.TebexPaymentResponse;
import br.com.craftlife.api.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TebexService {


    @Value("${application.tebex.secret}")
    private String tebexSecret;
    public void ativarProduto(Product product, String username, Double paidAmount) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tebex-Secret", tebexSecret);
        HttpEntity<TebexPaymentRequest> entity = new HttpEntity<>(TebexPaymentRequest.builder()
                .note("Pagamento via MercadoPago")
                .packages(List.of(TebexPaymentRequest.Packages.builder()
                        .id(product.getTebexId())
                        .build()))
                .price(paidAmount)
                .ign(username)
                .build(), headers);

        restTemplate.exchange("https://plugin.tebex.io/payments", HttpMethod.POST, entity, TebexPaymentResponse.class);
    }
}
