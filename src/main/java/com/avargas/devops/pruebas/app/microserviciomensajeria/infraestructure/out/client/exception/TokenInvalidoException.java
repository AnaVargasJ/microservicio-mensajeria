package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String mensaje) {
        super(mensaje);
    }
}
