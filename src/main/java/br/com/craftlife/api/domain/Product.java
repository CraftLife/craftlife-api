package br.com.craftlife.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;

    private String name;

    private String description;

    private String summary;

    private Double price;

    @JsonProperty("duration_days")
    private Double durationDays;

    @Column(name = "icon")
    private String icon;

    @Column(name = "icon_color")
    private String iconColor;

    @Column(name = "label")
    private String label;

    @Column(name = "label_color")
    private String labelColor;

    @Column(name = "image")
    private String image;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "display_order")
    private Integer displayOrder;

    @JsonIgnore
    @Column(name = "tebex_id")
    private String tebexId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_coupons",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;

}
