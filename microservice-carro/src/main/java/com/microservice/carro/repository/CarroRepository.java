package com.microservice.carro.repository;

import com.microservice.carro.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer> {

    //Así con Query de sql
    @Query("SELECT c FROM Carro c WHERE c.usuarioId = :idUsuario")
    List<Carro> findAllCarrosByUsuarioId(@Param("idUsuario") Integer idUsuario);

    //Así con Query method
    //List<Carro> findAllById(Integer idUsuario);

    @Modifying
    @Transactional
    @Query("DELETE FROM Carro c WHERE c.usuarioId = :idUsuario")
    void deleteAllCarrosByUsuarioId(@Param("idUsuario") Integer idUsuario);

}
