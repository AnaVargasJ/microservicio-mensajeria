package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MensajeResponse {

    SUCCES_NOTIFICATION("Notificación enviada al cliente.");
    private final String message;
}
