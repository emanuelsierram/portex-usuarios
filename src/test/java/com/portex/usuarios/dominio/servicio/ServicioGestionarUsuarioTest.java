package com.portex.usuarios.dominio.servicio;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.puerto.repositorio.RepositorioUsuario;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ServicioGestionarUsuarioTest {

    @Test
    void crearUsuarioDelegaEnRepositorio() {
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioGestionarUsuario servicio = new ServicioGestionarUsuario(repositorioUsuario);
        DtoUsuario usuario = new DtoUsuario("3010000000", "hash", "correo@test.com", false, false);

        servicio.crear(usuario);

        verify(repositorioUsuario).crear(usuario);
    }

    @Test
    void asignarRolDelegaEnRepositorio() {
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioGestionarUsuario servicio = new ServicioGestionarUsuario(repositorioUsuario);

        servicio.asignarRol("3010000000", "USER");

        verify(repositorioUsuario).asignarRol("3010000000", "USER");
    }

    @Test
    void eliminarRolDelegaEnRepositorio() {
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioGestionarUsuario servicio = new ServicioGestionarUsuario(repositorioUsuario);

        servicio.eliminarRol("3010000000", "USER");

        verify(repositorioUsuario).eliminarRol("3010000000", "USER");
    }

    @Test
    void eliminarUsuarioDelegaEnRepositorio() {
        RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
        ServicioGestionarUsuario servicio = new ServicioGestionarUsuario(repositorioUsuario);

        servicio.eliminar("3010000000");

        verify(repositorioUsuario).eliminar("3010000000");
    }
}
