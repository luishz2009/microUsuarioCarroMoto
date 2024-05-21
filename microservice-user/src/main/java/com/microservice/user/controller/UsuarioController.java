package com.microservice.user.controller;

import com.microservice.user.entity.Usuario;
import com.microservice.user.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios(){
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id){
        Usuario usuarioId = usuarioService.getUsuarioById(id);
        if (usuarioId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioId);
    }
    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario){
        Usuario usuario1 = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(usuario1);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuarioById(@PathVariable int id, @RequestBody Usuario usuario){
        Usuario editarUsuario = usuarioService.updateUsuarioById(id, usuario);
        return ResponseEntity.ok(editarUsuario);
    }
    @DeleteMapping("/{id}")
    public String deleteUsuarioById(@PathVariable int id){
        boolean ok = usuarioService.deleteUsuarioById(id);
        if (ok){
            return "El usuario con el id "+ id + " ha sido eliminado";
        }else {
            return "El usuario con el id "+ id + " no se pudo eliminar";
        }
    }


}
