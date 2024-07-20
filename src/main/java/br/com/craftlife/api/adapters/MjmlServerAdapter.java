package br.com.craftlife.api.adapters;

import br.com.craftlife.api.controller.dto.MjmlRequest;
import br.com.craftlife.api.controller.dto.MjmlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MjmlServerAdapter {

    @Value("${application.mjml.basic}")
    private String basic;

    public MjmlResponse renderMjmlToHtml(String mjmlTemplate) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + basic);

        HttpEntity<MjmlRequest> entity = new HttpEntity<>(MjmlRequest.builder()
                .mjml(mjmlTemplate)
                .build(), headers);

        return restTemplate.exchange("https://api.mjml.io/v1/render", HttpMethod.POST, entity, MjmlResponse.class).getBody();
    }
}
