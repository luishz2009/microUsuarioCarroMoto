package com.microservice.user.service;

import com.microservice.user.entity.Usuario;
import com.microservice.user.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuarioById(int id, Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.findById(id).get();
        nuevoUsuario.setName(usuario.getName());
        nuevoUsuario.setLastName(usuario.getLastName());
        nuevoUsuario.setEmail(usuario.getEmail());
        usuarioRepository.save(nuevoUsuario);

        return nuevoUsuario;

    }

    public Boolean deleteUsuarioById(int id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
