package com.microservice.user.client;

import com.microservice.user.dto.MotoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "microservice-moto", url = "localhost:8083/api/moto") //Para que funcione con eureka
@FeignClient(name = "microservice-moto") //Para que funcione con el gateway
public interface MotoClient {
    //Para que funcione con eureka
   /* @GetMapping("/findByIdUsuario/{idUsuario}")
    List<MotoDTO> findAllMotosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);

    @DeleteMapping("/deleteMotosByUsuarioId/{idUsuario}")
    void deleteAllMotosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);*/

    //Para que funcione con el gateway
    @GetMapping("/api/moto/findByIdUsuario/{idUsuario}")
    List<MotoDTO> findAllMotosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);

    @DeleteMapping("/api/moto/deleteMotosByUsuarioId/{idUsuario}")
    void deleteAllMotosByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);
}
