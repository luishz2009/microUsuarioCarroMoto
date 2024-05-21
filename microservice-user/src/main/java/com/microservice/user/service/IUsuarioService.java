package com.microservice.user.service;

import com.microservice.user.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> findAll();

    Usuario findById(int id);

    void save(Usuario usuario);


    void deleteUsuarioById(int id);
}
