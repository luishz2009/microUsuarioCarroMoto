package com.microservice.carro.client;

import com.microservice.user.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "microservice-user", url = "http://localhost:8081/api/usuario") //Para que funcione con eureka
@FeignClient(name = "microservice-user", url = "http://localhost:8080/api/usuario") //Para que funcione con el gateway
public interface UsuarioClient {
    @GetMapping("/{id}")
    ResponseEntity<Usuario> findUsuarioById(@PathVariable("id") int id);
}