package com.avargas.devops.pruebas.app.microserviciomensajeria.application.services.impl;

import com.avargas.devops.pruebas.app.microserviciomensajeria.application.services.IEnviarMensajeHandler;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api.SmsServicePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnviarMensajeHandler implements IEnviarMensajeHandler {

    private final SmsServicePort servicePort;

    @Override
    public void enviar(HttpServletRequest request , Long idUsuario) {
        String destinatario = "";
        SmsModel model = SmsModel.builder()
                .telefono(destinatario)
                .build();
        servicePort.enviarSms(model);
    }
}
