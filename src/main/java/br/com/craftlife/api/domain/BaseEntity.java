package br.com.craftlife.api.domain;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> extends Serializable {
    ID getId();
}
