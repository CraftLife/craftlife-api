package br.com.craftlife.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @JsonIgnoreProperties("products")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String name;

    private Double price;

    private Double discount;

    @Column(name = "icon")
    private String icon;

    @Column(name = "icon_color")
    private String iconColor;

    @Column(name = "label")
    private String label;

    @Column(name = "label_color")
    private String labelColor;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "display_order")
    private Integer displayOrder;

    @JsonIgnore
    @Column(name = "tebex_id")
    private String tebexId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("product")
    private List<Feature> features;

}
