package com.microservice.moto.controller;

import com.microservice.moto.client.UsuarioClient;
import com.microservice.moto.dto.MotoDTO;
import com.microservice.moto.entity.Moto;
import com.microservice.moto.service.MotoService;
import com.microservice.user.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moto")
public class MotoController {

    @Autowired
    MotoService motoService;

    @Autowired
    UsuarioClient usuarioClient;

    @GetMapping
    public ResponseEntity<List<Moto>> listaMotos(){
        List<Moto> carroList = motoService.findAll();
        if (carroList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carroList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> getMotoById(@PathVariable int id){
        Moto motoId = motoService.findById(id);
        if (motoId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motoId);
    }
    /*@PostMapping //Si el usuario no existe, entonces que no se pueda crear la moto
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMoto(@RequestBody Moto moto){
        motoService.save(moto);
    }*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveMoto(@RequestBody Moto moto){
        try {
            motoService.save(moto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}") //Si el usuario no existe, entonces que no se pueda actualizar la moto
    public ResponseEntity<?> updateMotoById(@PathVariable int id, @RequestBody MotoDTO motoDTO){
        Optional<Moto> motoOptional = Optional.ofNullable(motoService.findById(id));
        ResponseEntity<Usuario> response = usuarioClient.findUsuarioById(motoDTO.getUsuarioId());

        if (response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Usuario no encontrado");
        }

        if (motoOptional.isPresent()){
            Moto moto = motoOptional.get();
            moto.setMarca(motoDTO.getMarca());
            moto.setModelo(motoDTO.getModelo());
            moto.setUsuarioId(motoDTO.getUsuarioId());
            motoService.save(moto);
            return ResponseEntity.ok("Moto actualizada");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMotoById(@PathVariable Integer id){
        if (id != null){
            motoService.deleteById(id);
            return ResponseEntity.ok("La moto con el id "+id+ " ha sido eliminada");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findByIdUsuario/{idUsuario}")
    public ResponseEntity<?> findMotosByUsuarioId(@PathVariable Integer idUsuario){
        List<Moto> carros = motoService.findAllMotosByUsuarioId(idUsuario);
        if (carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    //Para eliminar todas las motos por id del usuario
    @DeleteMapping("/deleteMotosByUsuarioId/{idUsuario}")
    public ResponseEntity<?> deleteMotosByUsuarioId(@PathVariable Integer idUsuario) {
        try {
            motoService.deleteAllMotosByUsuarioId(idUsuario);
            return ResponseEntity.ok("Las motos del usuario " + idUsuario + " han sido eliminadas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar las motos: " + e.getMessage());
        }
    }
}
