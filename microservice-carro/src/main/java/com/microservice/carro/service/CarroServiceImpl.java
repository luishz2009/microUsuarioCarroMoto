package com.microservice.carro.service;

import com.microservice.carro.client.UsuarioClient;
import com.microservice.carro.entity.Carro;
import com.microservice.carro.repository.CarroRepository;
import com.microservice.user.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarroServiceImpl implements CarroService{
    @Autowired
    CarroRepository carroRepository;

    @Autowired
    UsuarioClient usuarioClient;

    @Override
    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    @Override
    public Carro findById(int id) {
        return carroRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Carro carro) {
        // Validar que el usuario existe
        ResponseEntity<Usuario> response = usuarioClient.findUsuarioById(carro.getUsuarioId());
        if (response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Usuario no encontrado");
        }
        carroRepository.save(carro);
    }

    @Override
    public List<Carro> findAllCarrosByIdUsuario(Integer idUsuario) {
        return carroRepository.findAllCarrosByUsuarioId(idUsuario);
    }
    @Override
    public void deleteById(int id) {
        carroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllCarrosByUsuarioId(Integer idUsuario) {
        carroRepository.deleteAllCarrosByUsuarioId(idUsuario);
    }


}
