package com.enesbayram.sm_core.base.service;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.model.base.BaseEntity;

import java.util.List;

public interface IBaseDbService <T extends BaseEntity> {

     <D extends DtoBase> D toDTO(T entity , Class<?> clazz);

     <D extends DtoBase> D toDTO(T t);

     <D extends DtoBase> List<D> toDTOList(List<T> list);


}
