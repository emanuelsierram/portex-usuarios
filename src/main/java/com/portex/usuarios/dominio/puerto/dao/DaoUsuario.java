package com.portex.usuarios.dominio.puerto.dao;

import com.portex.usuarios.dominio.modelo.dto.DtoRol;
import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;

import java.util.List;

public interface DaoUsuario {
    DtoUsuario consultarUsuarioPorId(String usuario);
    List<DtoRol> consultarRolesUsuarios(String usuario);
}
