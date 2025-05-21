package com.avargas.devops.pruebas.app.microserviciomensajeria.infraestructure.shared;

public final class SwaggerConstants {

    private SwaggerConstants() {}

    // === Tags ===
    public static final String NOTIFICACION = "Notificaciones";
    public static final String TAG_NOTIFICACIONES_DESC = "Aplicación que notifica a los clientes que el pedido esta listo";



    // === Operaciones - Usuario ===
    public static final String OP_NOTIFICACION_SUMMARY = "Enviar notificacion por el id cliente y envio de mensaje de notificacion de que el pedido esta listo";
    public static final String OP_NOTIFICACION_DESC = "Envio el mensaje filtrado por el numero de celular que da el idCliente";

    public static final String OP_BUSCAR_POR_ID_PARAMETER = "Id del usuario a buscar";
    public static final String OP_MENSAJE_PARAMETER = "Pin de notificacion";

    // === Descripciones de respuestas comunes ===
    public static final String RESPONSE_200_DESC = "Operación exitosa.";
    public static final String RESPONSE_404_DESC = "Recurso no encontrado.";
}