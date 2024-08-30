package br.com.craftlife.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties("category")
    private Product product;

    private String firstname;

    private String lastname;

    private String email;

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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "payment_status")
    private Status status;

    @Column(name = "date_approved")
    private LocalDateTime dateApproved;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    public enum Status {
        PENDING, APPROVED, COMPLETED
    }
}
