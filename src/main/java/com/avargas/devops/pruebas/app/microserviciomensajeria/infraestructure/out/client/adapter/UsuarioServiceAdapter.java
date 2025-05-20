package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.exception.ErrorException;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.exception.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.IGenericHttpClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UsuarioServiceAdapter implements UsuarioServicePort {

    private final IGenericHttpClient genericHttpClient;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    @Override
    public String numeroTelefono(Long idUsuario,  HttpServletRequest request) {
        String token = "Bearer " + request.getHeader("Authorization");
        String url = this.urlPropietarios + "/buscarPorIdUsuario/{idUsuario}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(idUsuario).toUriString();
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
        Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.GET, null, headers);

        if (response == null || !response.containsKey("respuesta")) {
            String mensaje = ErrorException.DATA_ERROR.getMessage() + idUsuario;
            throw new ValidacionNegocioException(mensaje);
        }

        Object respuestaObj = response.get("respuesta");
        if (!(respuestaObj instanceof Map)) {
            throw new ValidacionNegocioException(ErrorException.RESPONSE_ERROR.getMessage());
        }

        Map<?, ?> respuesta = (Map<?, ?>) respuestaObj;
        Object celular = respuesta.get("celular");
        if (!(celular instanceof String)) {
            throw new ValidacionNegocioException(ErrorException.ERROR_DATA_TYPE.getMessage());
        }
        return celular.toString();
    }
}