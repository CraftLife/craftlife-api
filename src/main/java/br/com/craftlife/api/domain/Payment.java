package br.com.craftlife.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "received_amount")
    private Double receivedAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "order_id")
    private Long orderId;

    private String status;

    @Column(name = "date_approved")
    private LocalDateTime dateApproved;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    private Boolean delivered;

}
