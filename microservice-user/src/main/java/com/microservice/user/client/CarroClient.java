package com.microservice.user.client;

import com.microservice.user.dto.CarroDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "microservice-carro", url = "localhost:8082/api/carro") //Para que funcione con eureka
@FeignClient(name = "microservice-carro") //Para que funcione con el gateway
public interface CarroClient {
   /* Para que funcione con eureka
   @GetMapping("/findByIdUsuario/{idUsuario}")
    List<CarroDTO> findAllCarrosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);

    @DeleteMapping("/deleteCarrosByUsuarioId/{idUsuario}")
    void deleteAllCarrosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);*/

    //Para que funcione con el gateway
    @GetMapping("/api/carro/findByIdUsuario/{idUsuario}")
    List<CarroDTO> findAllCarrosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);

    @DeleteMapping("/api/carro/deleteCarrosByUsuarioId/{idUsuario}")
    void deleteAllCarrosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);
}
