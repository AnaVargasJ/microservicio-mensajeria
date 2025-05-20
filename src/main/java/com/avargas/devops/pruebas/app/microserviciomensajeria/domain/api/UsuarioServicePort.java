package com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api;

import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioServicePort {
    String numeroTelefono(Long idUsuario,  HttpServletRequest request);
}
