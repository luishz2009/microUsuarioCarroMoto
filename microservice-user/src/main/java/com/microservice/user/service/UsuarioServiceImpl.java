package com.microservice.user.service;

import com.microservice.user.entity.Usuario;
import com.microservice.user.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario updateUsuarioById(int id, Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.findById(id).get();
        nuevoUsuario.setName(usuario.getName());
        nuevoUsuario.setLastName(usuario.getLastName());
        nuevoUsuario.setEmail(usuario.getEmail());
        usuarioRepository.save(nuevoUsuario);

        return nuevoUsuario;

    }
    @Override
    public void deleteUsuarioById(int id) {
            usuarioRepository.deleteById(id);
    }
}
