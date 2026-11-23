package com.portex.usuarios.infraestructura.seguridad.controlador;

import com.portex.usuarios.dominio.modelo.dto.DtoRol;
import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.puerto.dao.DaoUsuario;
import com.portex.usuarios.infraestructura.seguridad.jwt.JwtTokenManager;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IntrospeccionControladorTest {

    @Test
    void rechazaApiKeyInvalida() {
        JwtTokenManager jwtTokenManager = mock(JwtTokenManager.class);
        DaoUsuario daoUsuario = mock(DaoUsuario.class);
        IntrospeccionControlador controlador =
                new IntrospeccionControlador(jwtTokenManager, daoUsuario, "secreto-interno");

        ResponseEntity<IntrospeccionControlador.ResultadoIntrospeccion> respuesta =
                controlador.introspect("incorrecta", "Bearer token");

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }

    @Test
    void devuelveUsuarioActivoYRoles() {
        JwtTokenManager jwtTokenManager = mock(JwtTokenManager.class);
        DaoUsuario daoUsuario = mock(DaoUsuario.class);
        when(jwtTokenManager.esValido("token-valido")).thenReturn(true);
        when(jwtTokenManager.obtenerUsuario("token-valido")).thenReturn("3010000000");
        when(daoUsuario.consultarUsuarioPorId("3010000000"))
                .thenReturn(new DtoUsuario("3010000000", "hash", "correo@test.com", false, false));
        when(daoUsuario.consultarRolesUsuarios("3010000000"))
                .thenReturn(List.of(new DtoRol("3010000000", "USER", null)));

        IntrospeccionControlador controlador =
                new IntrospeccionControlador(jwtTokenManager, daoUsuario, "secreto-interno");

        ResponseEntity<IntrospeccionControlador.ResultadoIntrospeccion> respuesta =
                controlador.introspect(
                        "secreto-interno",
                        "Bearer token-valido"
                );

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().active());
        assertEquals("3010000000", respuesta.getBody().usuario());
        assertEquals(List.of("USER"), respuesta.getBody().roles());
    }

    @Test
    void devuelveInactivoCuandoJwtEsInvalido() {
        JwtTokenManager jwtTokenManager = mock(JwtTokenManager.class);
        DaoUsuario daoUsuario = mock(DaoUsuario.class);
        when(jwtTokenManager.esValido("token-invalido")).thenReturn(false);
        IntrospeccionControlador controlador =
                new IntrospeccionControlador(jwtTokenManager, daoUsuario, "secreto-interno");

        ResponseEntity<IntrospeccionControlador.ResultadoIntrospeccion> respuesta =
                controlador.introspect("secreto-interno", "Bearer token-invalido");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertFalse(respuesta.getBody().active());
    }
}
