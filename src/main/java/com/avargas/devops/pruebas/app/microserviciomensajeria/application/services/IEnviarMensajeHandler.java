package com.avargas.devops.pruebas.app.microserviciomensajeria.application.services;

import jakarta.servlet.http.HttpServletRequest;

public interface IEnviarMensajeHandler {

    void enviar(HttpServletRequest request, Long idUsuario);
}
