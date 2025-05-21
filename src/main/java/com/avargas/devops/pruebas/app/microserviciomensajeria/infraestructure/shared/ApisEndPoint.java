package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.shared;


public class ApisEndPoint {
    public static final String BASE_PATH_NOTIFICACIONES =  "/api/notificaciones";
    public static final String ENVIAR_NOTIFICACION_ID_USUARIO =  "/enviar-notificacion/{idUsuario}/pedido/{mensaje}";
    public static final String BUSCAR_USUARIO_POR_ID =  "/buscarPorIdUsuario/{idUsuario}";

    private ApisEndPoint() {
    }
}

