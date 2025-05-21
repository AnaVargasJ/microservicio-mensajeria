package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microserviciomensajeria.domain.api.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.exception.ErrorException;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.exception.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.IGenericHttpClient;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.shared.ApisEndPoint;
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

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String KEY_RESPUESTA = "respuesta";
    private static final String KEY_CELULAR = "celular";

    private final IGenericHttpClient genericHttpClient;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    @Override
    public String numeroTelefono(Long idUsuario, HttpServletRequest request) {
        String token = PREFIX_BEARER + request.getHeader(HEADER_AUTHORIZATION);
        String url = this.urlPropietarios + ApisEndPoint.BUSCAR_USUARIO_POR_ID;
        String finalUrl = UriComponentsBuilder.fromHttpUrl(url).buildAndExpand(idUsuario).toUriString();
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
        Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.GET, null, headers);

        if (response == null || !response.containsKey(KEY_RESPUESTA)) {
            throw new ValidacionNegocioException(ErrorException.DATA_ERROR.getMessage() + idUsuario);
        }

        Object respuestaObj = response.get(KEY_RESPUESTA);
        if (!(respuestaObj instanceof Map)) {
            throw new ValidacionNegocioException(ErrorException.RESPONSE_ERROR.getMessage());
        }

        Map<?, ?> respuesta = (Map<?, ?>) respuestaObj;
        Object celular = respuesta.get(KEY_CELULAR);
        if (!(celular instanceof String)) {
            throw new ValidacionNegocioException(ErrorException.ERROR_DATA_TYPE.getMessage());
        }

        return celular.toString();
    }
}
