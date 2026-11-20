package com.portex.usuarios.dominio.puerto.repositorio;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;

public interface RepositorioUsuario {
    void crear(DtoUsuario usuario);
    void asignarRol(String usuario, String rol);
}
