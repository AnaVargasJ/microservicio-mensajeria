package com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;

public interface SmsSenderPersistencePort {
    void enviar(SmsModel smsModel);
}
