package com.microservice.user.controller;

import com.microservice.user.client.CarroClient;
import com.microservice.user.client.MotoClient;
import com.microservice.user.dto.CarroDTO;
import com.microservice.user.dto.UsuarioDTO;
import com.microservice.user.entity.Usuario;
import com.microservice.user.exceptions.UsuarioNotFoundException;
import com.microservice.user.http.response.CarrosByUsuarioResponse;
import com.microservice.user.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    CarroClient carroClient;

    @Autowired
    MotoClient motoClient;

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
        try {
            usuarioService.deleteUsuarioById(id);
            carroClient.deleteAllCarrosByUsuarioId(id);
            motoClient.deleteAllMotosByUsuarioId(id);
            return ResponseEntity.ok("El usuario con el id " + id + " ha sido eliminado con todos sus vehículos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
    @GetMapping("/findCarrosByUsuarioId/{idUsuario}")
    public ResponseEntity<?> findAllCarrosByUsuarioId(@PathVariable Integer idUsuario){
        return ResponseEntity.ok(usuarioService.findAllCarrosByUsuarioId(idUsuario));
    }
    @GetMapping("/findMotosByUsuarioId/{idUsuario}")
    public ResponseEntity<?> findAllMotosByUsuarioId(@PathVariable Integer idUsuario){
        return ResponseEntity.ok(usuarioService.findAllMotosByUsuarioId(idUsuario));
    }
    @GetMapping("/allVehiclesByUSer/{idUsuario}")
    public ResponseEntity<Map<String, Object>> getCarrosAndMotosByUsuarioId(@PathVariable Integer idUsuario){
        Map<String, Object> resultado = usuarioService.getCarrosAndMotosByUsuarioId(idUsuario);
        return ResponseEntity.ok(resultado);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFoundException(UsuarioNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
