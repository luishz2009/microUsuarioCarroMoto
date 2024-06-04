package com.microservice.moto.service;

import com.microservice.moto.client.UsuarioClient;
import com.microservice.moto.entity.Moto;
import com.microservice.moto.repository.MotoRepository;
import com.microservice.user.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MotoServiceImpl implements MotoService{

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    UsuarioClient usuarioClient;

    @Override
    public List<Moto> findAll() {
        return motoRepository.findAll();
    }

    @Override
    public Moto findById(int id) {
        return motoRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Moto moto) {
        // Validar que el usuario existe
        ResponseEntity<Usuario> response = usuarioClient.findUsuarioById(moto.getUsuarioId());
        if (response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Usuario no encontrado");
        }
        motoRepository.save(moto);
    }

    @Override
    public List<Moto> findAllMotosByUsuarioId(Integer idUsuario) {
        return motoRepository.findAllMotosByUsuarioId(idUsuario);
    }

    @Override
    public void deleteById(int id) {
        motoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllMotosByUsuarioId(Integer idUsuario){
        motoRepository.deleteAllMotosByUsuarioId(idUsuario);
    }
}
