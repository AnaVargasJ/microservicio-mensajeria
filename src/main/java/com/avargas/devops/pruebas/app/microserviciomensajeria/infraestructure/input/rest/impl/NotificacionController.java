package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.input.rest.impl;

import com.avargas.devops.pruebas.app.microserviciomensajeria.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microserviciomensajeria.application.services.IEnviarMensajeHandler;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.input.rest.INotificacionController;
import com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.shared.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(ApisEndPoint.BASE_PATH_NOTIFICACIONES)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.NOTIFICACION, description = SwaggerConstants.TAG_NOTIFICACIONES_DESC)
public class NotificacionController implements INotificacionController {

    private final IEnviarMensajeHandler enviarMensajeHandler;
    @Override
    @PostMapping(ApisEndPoint.ENVIAR_NOTIFICACION_ID_USUARIO)
    @Operation(
            summary = SwaggerConstants.OP_NOTIFICACION_SUMMARY,
            description = SwaggerConstants.OP_NOTIFICACION_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = "Error de conversión de entidad a DTO",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> notificarPedidoListo(HttpServletRequest request,
                                                  @Parameter(description = SwaggerConstants.OP_BUSCAR_POR_ID_PARAMETER  , required = true)
                                                  @PathVariable Long idUsuario,
                                                  @Parameter(description = SwaggerConstants.OP_MENSAJE_PARAMETER  , required = true)
                                                  @PathVariable String mensaje) {
        enviarMensajeHandler.enviar(request, idUsuario, mensaje);
        return new ResponseEntity<>(ResponseUtil.success(MensajeResponse.SUCCES_NOTIFICATION.getMessage()), HttpStatus.OK);
    }
}
