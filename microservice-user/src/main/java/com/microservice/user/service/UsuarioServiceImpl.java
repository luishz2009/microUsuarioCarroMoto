package com.microservice.user.service;

import com.microservice.user.client.CarroClient;
import com.microservice.user.client.MotoClient;
import com.microservice.user.dto.CarroDTO;
import com.microservice.user.dto.MotoDTO;
import com.microservice.user.entity.Usuario;
import com.microservice.user.exceptions.UsuarioNotFoundException;
import com.microservice.user.http.response.CarrosByUsuarioResponse;
import com.microservice.user.http.response.MotosByUsuarioResponse;
import com.microservice.user.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CarroClient carroClient;

    @Autowired
    MotoClient motoClient;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(int id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario updateUsuarioById(int id, Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.findById(id).get();
        nuevoUsuario.setName(usuario.getName());
        nuevoUsuario.setLastName(usuario.getLastName());
        nuevoUsuario.setEmail(usuario.getEmail());
        usuarioRepository.save(nuevoUsuario);

        return nuevoUsuario;

    }
    @Override
    public void deleteUsuarioById(int id) {
        // Llamar al microservicio-carro para eliminar los carros del usuario
        carroClient.deleteAllCarrosByUsuarioId(id);

        // Llamar al microservicio-moto para eliminar las motos del usuario
        motoClient.deleteAllMotosByUsuarioId(id);

        // Eliminar el usuario
        usuarioRepository.deleteById(id);
    }

    @Override
    public CarrosByUsuarioResponse findAllCarrosByUsuarioId(Integer idUsuario) {
        //Consultar el usuario
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el id "+ idUsuario));

        //Obtener los carros por usuario
        List<CarroDTO> carroDTOList = carroClient.findAllCarrosByUsuarioId(idUsuario);

        return CarrosByUsuarioResponse.builder()
                .name(usuario.getName())
                .lastName(usuario.getLastName())
                .email(usuario.getEmail())
                .carroDTOList(carroDTOList)
                .build();
    }

    @Override
    public MotosByUsuarioResponse findAllMotosByUsuarioId(Integer idUsuario) {
        //Consultar el usuario
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el id "+ idUsuario));

        //Obtener las motos por usuario
        List<MotoDTO> motoDTOList = motoClient.findAllMotosByUsuarioId(idUsuario);

        return MotosByUsuarioResponse.builder()
                .name(usuario.getName())
                .lastName(usuario.getLastName())
                .email(usuario.getEmail())
                .motoDTOList(motoDTOList)
                .build();

    }

    @Override
    public Map<String, Object> getCarrosAndMotosByUsuarioId(Integer idUsuario) {
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el id "+ idUsuario));

        if (usuario == null){
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("usuario", usuario);

        List<CarroDTO> carroDTOList = carroClient.findAllCarrosByUsuarioId(idUsuario);
        if (carroDTOList.isEmpty()){
            resultado.put("Mensaje", "El usuario no tiene carros");
        }else {
            resultado.put("Carros", carroDTOList);
        }

        List<MotoDTO> motoDTOList = motoClient.findAllMotosByUsuarioId(idUsuario);
        if (motoDTOList.isEmpty()){
            resultado.put("Mensaje", "El usuario no tiene motos");
        }else {
            resultado.put("Motos", motoDTOList);
        }
        return resultado;
    }
}
