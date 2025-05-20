package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.exception;

public class ValidacionNegocioException extends RuntimeException{

    public ValidacionNegocioException(String message) {
        super(message);
    }
}
