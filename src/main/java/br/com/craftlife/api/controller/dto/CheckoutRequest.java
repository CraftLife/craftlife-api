package br.com.craftlife.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    private String name;

    private String surname;

    private String email;

    private String cpf;

    private String ign;

    @JsonProperty("product_id")
    private Long productId;

}
