package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.controllerAdvisor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MensajeError {

    ERROR_VALIDACION("Error de validación de negocio");

    private final String message;
}
