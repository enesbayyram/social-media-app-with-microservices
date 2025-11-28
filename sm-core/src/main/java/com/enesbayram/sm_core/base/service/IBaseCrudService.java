package com.enesbayram.sm_core.base.service;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.model.base.BaseEntity;

import java.util.List;

public interface IBaseCrudService<T extends BaseEntity> {

    T save(T clazz);

    T update(T clazz);

    void delete(T clazz);

    T findById(String id);

    Iterable<T> findAll();

    void flush();

    <D extends DtoBase> T toDaoForInsert(D dto);

    <D extends DtoBase> T toDaoForUpdate(D dto, T entity);
}
