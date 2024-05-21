package com.microservice.carro.service;

import com.microservice.carro.entity.Carro;
import com.microservice.carro.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroServiceImpl implements CarroService{
    @Autowired
    CarroRepository carroRepository;

    @Override
    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    @Override
    public Carro findById(int id) {
        return carroRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Carro carro) {
        carroRepository.save(carro);
    }

    @Override
    public List<Carro> findAllByIdUsuario(int id) {
        return carroRepository.findAllCarrosByUsuarioId(id);
    }

    @Override
    public void deleteById(int id) {
        carroRepository.deleteById(id);
    }

}
