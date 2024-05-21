package com.microservice.carro.repository;

import com.microservice.carro.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Integer> {

    //Así con Query de sql
    @Query("SELECT c FROM carros c WHERE c.usuarioId = :id")
    List<Carro> findAllCarrosByUsuarioId(int usuarioId);

    //Así con Query method
    //List<Carro> findAllById(Integer usuarioId);
}
