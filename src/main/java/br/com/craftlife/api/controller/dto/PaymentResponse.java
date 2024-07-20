package br.com.craftlife.api.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long id;

    private LocalDateTime dateApproved;

    private String username;

    private String payerName;

    private String payerEmail;

    private String productName;

    private Double transactionAmount;

    private Double amountFee;

    private String status;

    private Boolean processingStarted;

    private Boolean delivered;

}
