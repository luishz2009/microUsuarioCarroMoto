package com.microservice.user.service;

import com.microservice.user.entity.Usuario;
import com.microservice.user.http.response.CarrosByUsuarioResponse;
import com.microservice.user.http.response.MotosByUsuarioResponse;

import java.util.List;
import java.util.Map;

public interface IUsuarioService {

    List<Usuario> findAll();

    Usuario findById(int id);

    void save(Usuario usuario);

    void deleteUsuarioById(int id);
    CarrosByUsuarioResponse findAllCarrosByUsuarioId(Integer idUsuario);
    MotosByUsuarioResponse findAllMotosByUsuarioId(Integer idUsuario);
    Map<String, Object> getCarrosAndMotosByUsuarioId(Integer idUsuario);
}
