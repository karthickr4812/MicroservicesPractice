package com.karthick.dbdemo.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthick.dbdemo.model.UserEntity.UserEntity;
import com.karthick.dbdemo.repository.UserEntity.GenericRepository;

@Service
@Transactional
public abstract class GenericUserServiceImpl<T extends UserEntity<ID>, ID extends Serializable>
        implements GenericUserService<T, ID>{
    
    private GenericRepository<T, ID> genericRepository;
    
    @Autowired
    public GenericUserServiceImpl(GenericRepository<T, ID> genericRepository) {
        this.genericRepository = genericRepository;
    }
    
    @Override
    public T save(T entity) {
        return (T) genericRepository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public Optional<T> findById(ID entityId) {
        return genericRepository.findById(entityId);
    }

    @Override
    public T update(T entity) {
        return (T) genericRepository.save(entity);
    }

    @Override
    public T updateById(T entity, ID entityId) {
        Optional<T> optional = genericRepository.findById(entityId);
        if(optional.isPresent()){
            return (T) genericRepository.save(entity);
        }else{
            return null;
        }
    }

    @Override
    public void delete(T entity) {
    	genericRepository.delete(entity);
    }

    @Override
    public void deleteById(ID entityId) {
    	genericRepository.deleteById(entityId);
    }

}