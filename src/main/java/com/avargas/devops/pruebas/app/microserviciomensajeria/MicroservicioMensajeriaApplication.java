package com.avargas.devops.pruebas.app.microserviciomensajeria;

import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.config.TwilioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TwilioConfig.class)
public class MicroservicioMensajeriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioMensajeriaApplication.class, args);
    }

}
