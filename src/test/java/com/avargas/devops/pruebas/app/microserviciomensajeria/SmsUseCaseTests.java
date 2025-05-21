package com.avargas.devops.pruebas.app.microserviciomensajeria;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.model.SmsModel;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi.SmsSenderPersistencePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.usecase.SmsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SmsUseCaseTests {

    @Mock
    private SmsSenderPersistencePort smsSenderPersistencePort;

    @InjectMocks
    private SmsUseCase smsUseCase;

    @Test
    void enviarSms_deberiaLlamarAlAdapterConElModelo() {
        SmsModel smsModel = mock(SmsModel.class);

        smsUseCase.enviarSms(smsModel);

        verify(smsSenderPersistencePort).enviar(smsModel);
    }
}
