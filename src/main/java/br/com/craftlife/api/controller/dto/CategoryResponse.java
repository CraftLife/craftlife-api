package br.com.craftlife.api.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private Long id;

    private String name;

    private String title;

    private String description;

    private String icon;

    private String iconColor;

//    @ManyToOne
//    @JoinColumn(name = "parent_category_id")
//    private Category parentCategory;
//
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
//    private Set<Category> childrenCategories = new HashSet<>();

    private List<ProductResponse> products;

}
