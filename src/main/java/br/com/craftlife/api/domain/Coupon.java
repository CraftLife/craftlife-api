package br.com.craftlife.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "type")
    private Coupon.Type type;

    @Column(name = "discount")
    private Double discount;
    @Column(name = "required_min_value")
    private Double requiredMinValue;

    @Column(name = "auto_apply")
    private Boolean auto_apply;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ManyToMany(mappedBy = "coupons")
    private Set<Product> products = new HashSet<>();

    public enum Type {
        PERCENTAGE, FIXED_VALUE
    }
}