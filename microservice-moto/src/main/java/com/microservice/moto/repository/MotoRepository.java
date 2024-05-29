package com.microservice.moto.repository;

import com.microservice.moto.entity.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer> {

    @Query("SELECT m FROM Moto m WHERE m.usuarioId = :idUsuario")
    List<Moto> findAllMotosByUsuarioId(@Param("idUsuario") Integer idUsuario);

    @Modifying
    @Transactional
    @Query("DELETE FROM Moto m WHERE m.usuarioId = :idUsuario")
    void deleteAllMotosByUsuarioId(@Param("idUsuario") Integer idUsuario);
}
