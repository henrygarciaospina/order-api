package com.codmind.orderapi.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<E, D> {

    public abstract D fromEntity(E entity);
    public abstract E fromDTO(D dto);

    public List<D> fromEntity(List<E> entitys){
        return entitys.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    public List<E> fromDTO(List<D> dtos){
        return dtos.stream()
                .map(this::fromDTO)
                .collect(Collectors.toList());
    }
}
