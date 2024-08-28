package br.com.craftlife.api.controller;

import br.com.craftlife.api.domain.BaseEntity;
import br.com.craftlife.api.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("/api/{entityName}")
public class CrudController<T extends BaseEntity<ID>, ID extends Serializable> {

    private final CrudService<T, ID> genericService;

    public CrudController(CrudService<T, ID> genericService) {
        this.genericService = genericService;
    }

    @GetMapping
    public Page<T> getAll(
            @PathVariable String entityName,
            @RequestParam Map<String, String> allParams,
            Pageable pageable
    ) {
        allParams.remove("page");
        allParams.remove("size");
        allParams.remove("sort");
        return genericService.findAll(entityName, allParams, pageable);
    }
}