package br.com.craftlife.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoalResponse {

    @JsonProperty
    private Double goal;

    @JsonProperty
    private Double collected;

    @JsonProperty
    private List<RsDonation> lastDonations;

    @Data
    @Builder
    public static class RsDonation {

        @JsonProperty
        private String username;

        @JsonProperty
        private String productName;

        @JsonProperty
        private Double transactionAmount;

    }
}
