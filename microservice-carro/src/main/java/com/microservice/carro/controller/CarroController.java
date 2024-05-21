package com.microservice.carro.controller;

import com.microservice.carro.entity.Carro;
import com.microservice.carro.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    CarroService carroService;

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


}
