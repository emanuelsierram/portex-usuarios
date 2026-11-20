package com.portex.usuarios.infraestructura.excepcion;

public class ExcepcionTecnica extends RuntimeException {

    public ExcepcionTecnica(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
