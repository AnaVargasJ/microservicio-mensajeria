package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.security.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.avargas.devops.pruebas.app.microserviciomensajeria.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.IGenericHttpClient;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.out.client.exception.TokenInvalidoException;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.security.jwt.TokenJwtConfig;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.security.model.UsuarioAutenticado;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Profile("!test")
public class JwtValidationFilter extends BasicAuthenticationFilter {

    private static final String PATH_CONSULTAR_USUARIO = "/buscarPorCorreo/{correo}";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String FIELD_ID_USUARIO = "idUsuario";
    private static final String FIELD_ROL = "rol";
    private static final String FIELD_NOMBRE = "nombre";
    private static final String FIELD_CODIGO = "codigo";
    private static final String FIELD_RESPUESTA = "respuesta";
    private static final int HTTP_OK = 200;
    private static final String ERROR_TOKEN_VENCIDO = "El token ha vencido";
    private static final String ERROR_TOKEN_INVALIDO = "Token inválido: ";
    private static final String ERROR_CONSULTA_USUARIO = "Error al consultar el usuario con correo: ";

    private final IGenericHttpClient loginClient;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    public JwtValidationFilter(AuthenticationManager authManager, IGenericHttpClient loginClient) {
        super(authManager);
        this.loginClient = loginClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            if (isTokenExpired(decodedJWT.getExpiresAt())) {
                reject(response, ERROR_TOKEN_VENCIDO, HttpStatus.UNAUTHORIZED);
                return;
            }

            String correo = decodedJWT.getSubject();
            Map<String, Object> usuario = consultarUsuarioPorCorreo(correo, header);

            Long id = Long.valueOf(usuario.get(FIELD_ID_USUARIO).toString());
            String rol = ((Map<String, Object>) usuario.get(FIELD_ROL)).get(FIELD_NOMBRE).toString();

            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(ROLE_PREFIX + rol));

            UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado(
                    id,
                    correo,
                    null,
                    authorities
            );

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("Error en validación de token: {}", ex.getMessage());
            reject(response, ERROR_TOKEN_INVALIDO + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, Object> consultarUsuarioPorCorreo(String correo, String tokenHeader) {
        String url = urlPropietarios + PATH_CONSULTAR_USUARIO;
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, tokenHeader);

        Map<String, Object> response = loginClient.sendRequest(
                url.replace("{correo}", correo), HttpMethod.GET, null, headers
        );

        if (response == null || ((Number) response.get(FIELD_CODIGO)).intValue() != HTTP_OK) {
            throw new TokenInvalidoException(ERROR_CONSULTA_USUARIO + correo);
        }

        return (Map<String, Object>) response.get(FIELD_RESPUESTA);
    }

    private boolean isTokenExpired(Date exp) {
        return exp.before(new Date());
    }

    private void reject(HttpServletResponse response, String mensaje, HttpStatus status) throws IOException {
        ResponseDTO error = ResponseDTO.builder()
                .mensaje(mensaje)
                .codigo(status.value())
                .build();
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
