package com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;

public interface SmsServicePort {
    void enviarSms(SmsModel smsModel);
}
