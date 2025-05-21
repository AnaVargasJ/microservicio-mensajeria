package com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SmsModel {
    private String telefono;
    private String mensaje;
}
