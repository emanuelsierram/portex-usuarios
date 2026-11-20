package com.portex.usuarios.infraestructura.controlador;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.servicio.ServicioGestionarUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioControladorTest {

    @Test
    void crearCodificaContrasenaAntesDeCrearUsuario() {
        ServicioGestionarUsuario servicio = mock(ServicioGestionarUsuario.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode("secreta")).thenReturn("hash");
        UsuarioControlador controlador = new UsuarioControlador(servicio, passwordEncoder);

        ResponseEntity<Void> respuesta = controlador.crear(
                new DtoUsuario("3010000000", "secreta", "correo@test.com", false, false));

        verify(servicio).crear(argThat(usuario ->
                "3010000000".equals(usuario.getUsuario())
                        && "hash".equals(usuario.getContrasena())
                        && "correo@test.com".equals(usuario.getEmail())
                        && !usuario.getEstaDesabilitado()
                        && !usuario.getEstaBloqueado()));
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    void asignarRolRetornaEstadoCreado() {
        ServicioGestionarUsuario servicio = mock(ServicioGestionarUsuario.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UsuarioControlador controlador = new UsuarioControlador(servicio, passwordEncoder);

        ResponseEntity<Void> respuesta = controlador.asignarRol("3010000000", "USER");

        verify(servicio).asignarRol("3010000000", "USER");
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }
}
