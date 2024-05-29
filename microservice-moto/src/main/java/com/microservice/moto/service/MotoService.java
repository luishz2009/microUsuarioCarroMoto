package com.microservice.moto.service;

import com.microservice.moto.entity.Moto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MotoService {

    List<Moto> findAll();

    Moto findById(int id);

    void save(Moto moto);

    List<Moto> findAllMotosByUsuarioId(Integer idUsuario);

    void deleteById(int id);

    @Transactional
    void deleteAllMotosByUsuarioId(Integer idUsuario);
}
