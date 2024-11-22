package br.com.craftlife.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryTreeResponse {

    private Long id;

    private String name;

    private String title;

    private String description;

    private String icon;

    private String iconColor;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryTreeResponse> childrenCategories = new ArrayList<>();

}
