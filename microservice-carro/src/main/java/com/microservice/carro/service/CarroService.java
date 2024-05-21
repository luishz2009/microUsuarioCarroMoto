package com.microservice.carro.service;

import com.microservice.carro.entity.Carro;

import java.util.List;

public interface CarroService {

    List<Carro> findAll();

    Carro findById(int id);

    void save(Carro carro);

    List<Carro> findAllByIdUsuario(int id);

    void deleteById(int id);

}
