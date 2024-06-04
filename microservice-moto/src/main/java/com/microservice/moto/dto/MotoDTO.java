package com.microservice.moto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotoDTO {

    private Integer id;
    private String marca;
    private String modelo;
    private int usuarioId;
}
