package br.com.craftlife.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MjmlResponse {

    private String html;

    private List<String> errors;

    private String mjml;

    @JsonProperty("mjml_version")
    private String mjmlVersion;

}
