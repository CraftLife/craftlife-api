package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.SearchCriteria;
import br.com.craftlife.api.domain.BaseEntity;
import br.com.craftlife.api.repository.CrudRepository;
import br.com.craftlife.api.repository.CrudSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrudService<T extends BaseEntity<ID>, ID extends Serializable> {

    private final ApplicationContext applicationContext;

    public Page<T> findAll(String entityName, Map<String, String> params, Pageable pageable) {
        CrudRepository<T, ID> repository = getRepository(entityName);

        CrudSpecification<T> specification = new CrudSpecification<>();

        params.forEach((key, value) -> {
            String[] parts = key.split("\\.");
            if (parts.length == 2) {
                String field = parts[0];
                String operation = parts[1];
                specification.add(new SearchCriteria(field, operation, value));
            } else {
                specification.add(new SearchCriteria(key, "equal", value));
            }
        });

        return repository.findAll(specification, pageable);
    }

    @SuppressWarnings("unchecked")
    private CrudRepository<T, ID> getRepository(String entityName) {
        String repositoryBeanName = entityName + "Repository";
        return (CrudRepository<T, ID>) applicationContext.getBean(repositoryBeanName);
    }
}