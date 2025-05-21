package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.input.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface INotificacionController {

    ResponseEntity<?>  notificarPedidoListo(HttpServletRequest request, Long idUsuario, String mensaje);
}
