package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi.SmsSenderPersistencePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TwilioSmsAdapter implements SmsSenderPersistencePort {

    private final TwilioConfig twilioConfig;
    @Override
    public void enviar(SmsModel smsModel) {

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + smsModel.getTelefono()),
                new PhoneNumber("whatsapp:" + twilioConfig.getPhoneNumber()),
                smsModel.getMensaje()
        ).create();

        log.info("Mensaje enviado. SID: " + message.getSid());

    }
}
