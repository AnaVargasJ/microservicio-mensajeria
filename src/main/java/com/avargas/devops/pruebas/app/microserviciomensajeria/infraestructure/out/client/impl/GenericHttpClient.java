package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.impl;

import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.IGenericHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.FormInserter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GenericHttpClient implements IGenericHttpClient {

    private final WebClient.Builder webClientBuilder;


    public GenericHttpClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Map<String, Object> sendRequest(String url, HttpMethod method, Map<String, Object> body,
                                           Map<String, String> headers) {

        WebClient.RequestBodySpec requestSpec = webClientBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .method(method)
                .uri(url);

        // Agregar autenticación básica si se detecta llamada a Twilio
        if (url.contains("twilio.com") && headers != null &&
                headers.containsKey("X-TWILIO-AUTH-SID") &&
                headers.containsKey("X-TWILIO-AUTH-TOKEN")) {

            String username = headers.remove("X-TWILIO-AUTH-SID");
            String password = headers.remove("X-TWILIO-AUTH-TOKEN");
            requestSpec = requestSpec.headers(h -> h.setBasicAuth(username, password));
        }

        WebClient.RequestHeadersSpec<?> headersSpec;

        boolean isFormUrlEncoded = headers != null &&
                "application/x-www-form-urlencoded".equalsIgnoreCase(headers.get("Content-Type"));

        if (isFormUrlEncoded && body != null) {
            FormInserter<String> formData = BodyInserters.fromFormData("", "");
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                formData = formData.with(entry.getKey(), entry.getValue().toString());
            }
            headersSpec = requestSpec.body(formData);
        } else if (body != null) {
            headersSpec = requestSpec.bodyValue(body);
        } else {
            headersSpec = requestSpec;
        }

        // Agregar headers al request
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headersSpec = headersSpec.header(entry.getKey(), entry.getValue());
            }
        }

        try {
            String rawResponse = headersSpec
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap;

            try {
                responseMap = mapper.readValue(rawResponse, Map.class);
            } catch (Exception jsonEx) {
                responseMap = new HashMap<>();
                responseMap.put("mensaje", "Error al parsear la respuesta JSON");
                responseMap.put("respuesta_cruda", rawResponse);
                responseMap.put("error_detalle", jsonEx.getMessage());
            }

            return responseMap;

        } catch (Exception e) {
            log.error("Error en la solicitud HTTP: {}", e.getMessage(), e);
            throw new RuntimeException("Error en la solicitud HTTP: " + e.getMessage());
        }
    }
}
