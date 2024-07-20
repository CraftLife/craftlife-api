package br.com.craftlife.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TebexPaymentRequest {

    private String note;

    private List<Packages> packages;

    private Double price;

    private String ign;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Packages {

        private String id;

    }

}
