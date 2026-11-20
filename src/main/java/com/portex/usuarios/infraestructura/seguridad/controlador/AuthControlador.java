package com.portex.usuarios.infraestructura.seguridad.controlador;

import com.portex.usuarios.dominio.modelo.entidad.Login;
import com.portex.usuarios.dominio.servicio.ServicioIniciarSesion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthControlador {

    private final AuthenticationManager authenticationManager;
    private final ServicioIniciarSesion servicioIniciarSesion;

    public AuthControlador(AuthenticationManager authenticationManager, ServicioIniciarSesion servicioIniciarSesion) {
        this.authenticationManager = authenticationManager;
        this.servicioIniciarSesion = servicioIniciarSesion;
    }

    @PostMapping
    public ResponseEntity<Void> login(@RequestBody Login login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsuario(), login.getContrasena()));
        String jwt = servicioIniciarSesion.ejecutar(login);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }
}
