package com.microservice.user.controller;

import com.microservice.user.dto.UsuarioDTO;
import com.microservice.user.entity.Usuario;
import com.microservice.user.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody Usuario usuario){
        usuarioService.save(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable int id){
        Usuario usuarioId = usuarioService.findById(id);
        if (usuarioId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuarioById(@PathVariable int id, @RequestBody UsuarioDTO usuarioDTO){
        Optional<Usuario> usuarioOptional = Optional.ofNullable(usuarioService.findById(id));
        if (usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setName(usuarioDTO.getName());
            usuario.setLastName(usuarioDTO.getLastName());
            usuario.setEmail(usuarioDTO.getEmail());
            usuarioService.save(usuario);

            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        if (id != null){
            usuarioService.deleteUsuarioById(id);
            return ResponseEntity.ok("El usuario con el id "+id+ " ha sido eliminado");
        }
        return ResponseEntity.notFound().build();
    }
}
