package com.familia.llamada

/**
 * Único archivo que hay que editar para personalizar la app.
 */
object Config {

    /** Servidor Jitsi. Puede ser meet.jit.si, un servidor propio o 8x8.vc (JaaS). */
    const val SERVER_URL = "https://meet.jit.si"

    /**
     * Nombre de la sala familiar. Los dos hijos entran a ESTA misma sala.
     * En un servidor público cualquiera que adivine el nombre puede entrar,
     * así que déjalo largo y raro. Cámbialo por uno propio.
     */
    const val ROOM = "LlamadaMamaH7k2Qx9RpW4Zt8"

    /** Nombre que verán los hijos en la pantalla cuando ella entre. */
    const val MY_NAME = "Mamá"

    /** Saludo grande que aparece arriba en la pantalla principal. */
    const val TITLE = "Hola, Mamá"

    /**
     * Teléfonos de los 2 hijos (con código de país). Al tocar el botón, la app les manda
     * un SMS con el enlace de la sala y entra a la llamada.
     * Ejemplo: listOf("+584141234567", "+584167654321")
     * Si se deja vacío, solo entra a la llamada (sin avisar).
     */
    val CHILDREN_PHONES: List<"+584143734512", "+584143507101"> = emptyList("+584143734512", "+584143507101")

    /** Texto del aviso que reciben los hijos. */
    const val ALERT_MESSAGE = "Mamá quiere hablar con ustedes. Entren a la videollamada:"

    /** No reenviar el SMS si ella vuelve a tocar el botón dentro de este tiempo. */
    const val SMS_COOLDOWN_MINUTES = 5
}
