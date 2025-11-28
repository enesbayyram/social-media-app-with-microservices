package com.enesbayram.sm_core.base.service.impl;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.model.base.BaseEntity;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseDbServiceImpl<E extends BaseDaoRepository<T>, T extends BaseEntity>
        implements IBaseDbService<T>, IBaseCrudService<T> {

    @Autowired
    protected E dao;

    @PersistenceContext
    private EntityManager entityManager;

    public abstract Class<? extends  DtoBase> getDTOClassForService();


    private Class<T> getClazz() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public T save(T clazz) {
        return dao.save(clazz);
    }

    @Override
    public T update(T clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazz.setUpdateUser("ebayram");
        return entityManager.merge(clazz);
    }

    @Override
    public void delete(T clazz) {
        dao.delete(clazz);
    }

    @Override
    public T findById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Iterable<T> findAll() {
        return dao.findAll();
    }

    @Override
    public void flush() {
        dao.flush();
    }

    public <D extends  DtoBase> D toDTO(T entity){
        return toDTO(entity, getDTOClassForService());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D extends DtoBase> D toDTO(T entity , Class<?> clazz) {
        D dtoBase;
        try {
            dtoBase = (D) clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dtoBase);
            return dtoBase;
        } catch (Exception e) {
            throw new RuntimeException("toDTO method error : " + e.getMessage());
        }
    }

    @Override
    public <D extends DtoBase> List<D> toDTOList(List<T> list) {
        List<D> dtoList = new ArrayList<>();
        for (T t : list) {
            dtoList.add(toDTO(t));
        }
        return dtoList;
    }

    @Override
    public <D extends DtoBase> T toDaoForInsert(D dto) {
        T entity;
        try {
            entity = (T) getClazz().getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, entity);

            entity.setCreateTime(LocalDateTime.now());
            entity.setCreateUser("ebayram");

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("toDaoForInsert method error : " + e.getMessage());
        }
    }

    @Override
    public <D extends DtoBase> T toDaoForUpdate(D dto, T entity) {
        T model;
        try {
            model = (T) getClazz().getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, model);

            model.setId(entity.getId());
            model.setCreateUser(entity.getCreateUser());
            model.setCreateTime(entity.getCreateTime());

            return model;
        } catch (Exception e) {
            throw new RuntimeException("toDaoForUpdate method error : " + e.getMessage());
        }
    }

}
