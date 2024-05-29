package com.microservice.user.http.response;

import com.microservice.user.dto.CarroDTO;
import com.microservice.user.dto.MotoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotosByUsuarioResponse {
    private String name;
    private String lastName;
    private String email;
    private List<MotoDTO> motoDTOList;
}
