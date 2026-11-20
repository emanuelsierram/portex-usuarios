package com.portex.usuarios.dominio.servicio;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.puerto.repositorio.RepositorioUsuario;

public class ServicioGestionarUsuario {

    private final RepositorioUsuario repositorioUsuario;

    public ServicioGestionarUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public void crear(DtoUsuario usuario) {
        repositorioUsuario.crear(usuario);
    }

    public void asignarRol(String usuario, String rol) {
        repositorioUsuario.asignarRol(usuario, rol);
    }
}
