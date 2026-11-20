package com.portex.usuarios.infraestructura.configuracion;

import com.portex.usuarios.dominio.servicio.ServicioIniciarSesion;
import com.portex.usuarios.dominio.puerto.repositorio.RepositorioUsuario;
import com.portex.usuarios.dominio.servicio.ServicioGestionarUsuario;
import com.portex.usuarios.infraestructura.seguridad.jwt.JwtTokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {

    @Bean
    public ServicioIniciarSesion servicioIniciarSesion(JwtTokenManager jwtTokenManager) {
        return new ServicioIniciarSesion(jwtTokenManager);
    }

    @Bean
    public ServicioGestionarUsuario servicioGestionarUsuario(RepositorioUsuario repositorioUsuario) {
        return new ServicioGestionarUsuario(repositorioUsuario);
    }
}
