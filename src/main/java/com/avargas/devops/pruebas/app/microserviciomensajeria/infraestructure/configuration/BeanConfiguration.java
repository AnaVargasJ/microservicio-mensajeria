package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.configuration;


import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api.SmsServicePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.spi.SmsSenderPersistencePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.usecase.SmsUseCase;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.IGenericHttpClient;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.impl.GenericHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public IGenericHttpClient genericHttpClient(WebClient.Builder builder) {
        return new GenericHttpClient(builder);
    }

    @Bean
    public SmsServicePort smsServicePort(SmsSenderPersistencePort port) {
        return new SmsUseCase(port);
    }
}
