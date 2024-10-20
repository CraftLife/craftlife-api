package br.com.craftlife.api.controller.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private Long ID;

    private String name;

    private String description;

    private Double price;

    private String icon;

    private String iconColor;

    private String label;

    private String labelColor;

    private String image;

}
