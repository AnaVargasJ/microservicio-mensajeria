package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.configuration;


import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api.SmsServicePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi.SmsSenderPersistencePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.usecase.SmsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final SmsSenderPersistencePort smsSenderPersistencePort;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public SmsServicePort smsServicePort(){
        return new SmsUseCase(smsSenderPersistencePort);
    }
}
