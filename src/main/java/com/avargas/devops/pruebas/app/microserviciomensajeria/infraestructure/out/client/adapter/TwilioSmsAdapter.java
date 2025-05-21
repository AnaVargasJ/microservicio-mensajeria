package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi.SmsSenderPersistencePort;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.twilio.Twilio;


@Component
@RequiredArgsConstructor
@Slf4j
public class TwilioSmsAdapter implements SmsSenderPersistencePort {

    @Value("${twilio.account-sid}")
    private String accountSid ;

    @Value("${twilio.auth-token}")
    private String authToken ;

    @Value("${twilio.phone-number}")
    private String twilioNumber;

    @Override
    public void enviar(SmsModel smsModel) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(smsModel.getTelefono()),
                        new com.twilio.type.PhoneNumber(twilioNumber),
                        smsModel.getMensaje())
                .create();

        log.info(message.getSid());
    }
}
