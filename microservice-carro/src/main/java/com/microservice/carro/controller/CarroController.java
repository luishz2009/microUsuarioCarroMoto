package com.microservice.carro.controller;

import com.microservice.carro.client.UsuarioClient;
import com.microservice.carro.dto.CarroDTO;
import com.microservice.carro.entity.Carro;
import com.microservice.carro.service.CarroService;
import com.microservice.user.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carro")
public class CarroController {

    @Autowired
    CarroService carroService;

    @Autowired
    UsuarioClient usuarioClient;

    @GetMapping
    public ResponseEntity<List<Carro>> listaCarros(){
        List<Carro> carroList = carroService.findAll();
        if (carroList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carroList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarroById(@PathVariable int id){
        Carro carroId = carroService.findById(id);
        if (carroId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carroId);
    }
   /* @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCarro(@RequestBody Carro carro){
        carroService.save(carro);
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveCarro(@RequestBody Carro carro){
        //Si el usuario no existe, entonces que no se pueda crear el carro
        try {
            carroService.save(carro);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable int id, @RequestBody CarroDTO carroDTO){
        Optional<Carro> carroOptional = Optional.ofNullable(carroService.findById(id));
        ResponseEntity<Usuario> response = usuarioClient.findUsuarioById(carroDTO.getUsuarioId());

        //Si el usuario no existe, entonces que no se pueda actualizar el carro
        if (response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Usuario no encontrado");
        }
        if (carroOptional.isPresent()){
            Carro carro = carroOptional.get();
            carro.setMarca(carroDTO.getMarca());
            carro.setModelo(carroDTO.getModelo());
            carro.setUsuarioId(carroDTO.getUsuarioId());
            carroService.save(carro);
            return ResponseEntity.ok("Carro actualizado");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        if (id != null){
            carroService.deleteById(id);
            return ResponseEntity.ok("El carro con el id "+id+ " ha sido eliminado");
            }
            return ResponseEntity.notFound().build();
        }
    //Buscar carros por el id del usuario
    @GetMapping("/findByIdUsuario/{idUsuario}")
    public ResponseEntity<?> findCarrosByUsuarioId(@PathVariable Integer idUsuario){
        List<Carro> carros = carroService.findAllCarrosByIdUsuario(idUsuario);
        if (carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    //Para eliminar todos los carros por id del usuario
    @DeleteMapping("/deleteCarrosByUsuarioId/{idUsuario}")
    public ResponseEntity<?> deleteCarrosByUsuarioId(@PathVariable Integer idUsuario) {
        try {
            carroService.deleteAllCarrosByUsuarioId(idUsuario);
            return ResponseEntity.ok("Los carros del usuario " + idUsuario + " han sido eliminados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar los carros: " + e.getMessage());
        }
    }

}
