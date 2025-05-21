package com.avargas.devops.pruebas.app.microserviciomensajeria.domain.usecase;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api.SmsServicePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi.SmsSenderPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SmsUseCase implements SmsServicePort {
    private final SmsSenderPersistencePort smsSenderPersistencePort;
    @Override
    public void enviarSms(SmsModel smsModel) {
        smsSenderPersistencePort.enviar(smsModel);
    }
}
