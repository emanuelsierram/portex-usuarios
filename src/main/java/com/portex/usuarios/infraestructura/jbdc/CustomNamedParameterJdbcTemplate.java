package com.portex.usuarios.infraestructura.jbdc;

import com.portex.usuarios.infraestructura.excepcion.ExcepcionTecnica;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Repository
public class CustomNamedParameterJdbcTemplate {

    private static final String ERROR_PARAMETROS = "Error obteniendo el nombre y valor de objeto";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void actualizar(Object object, String sql) {
        namedParameterJdbcTemplate.update(sql, crearParametros(object));
    }

    private MapSqlParameterSource crearParametros(Object object) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    paramSource.addValue(field.getName(), field.get(object));
                } catch (IllegalAccessException exception) {
                    throw new ExcepcionTecnica(ERROR_PARAMETROS, exception);
                }
            }
        }
        return paramSource;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}
