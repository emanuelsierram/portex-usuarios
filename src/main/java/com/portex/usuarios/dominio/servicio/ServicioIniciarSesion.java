package com.portex.usuarios.dominio.servicio;

import com.portex.usuarios.dominio.modelo.entidad.Login;
import com.portex.usuarios.infraestructura.seguridad.jwt.JwtTokenManager;

public class ServicioIniciarSesion {

    private final JwtTokenManager jwtTokenManager;

    public ServicioIniciarSesion(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    public String ejecutar(Login login) {
        return jwtTokenManager.crear(login.getUsuario());
    }
}
