# Llamada Familiar (Android + Jitsi Meet)

App con **un solo botón gigante**: "Llamar a mis hijos".

Al tocarlo:
1. Manda un SMS con el enlace de la sala a los dos hijos (si pusiste sus teléfonos en `Config.kt`).
2. Entra a la sala de videollamada con cámara y micrófono encendidos.

El hijo y la hija abren el enlace y quedan los tres en una llamada conjunta.

## Cómo compilarla

1. Instala [Android Studio](https://developer.android.com/studio).
2. `File > Open` y elige la carpeta `LlamadaFamiliar`. Espera a que termine el "Gradle Sync".
3. Edita `app/src/main/java/com/familia/llamada/Config.kt`:
   - `ROOM`: pon un nombre de sala largo y único.
   - `MY_NAME` y `TITLE`: nombre de la mamá/papá.
   - `CHILDREN_PHONES`: los teléfonos de los 2 hijos con código de país.
4. Conecta el teléfono Android (con "Depuración USB" activada) y pulsa Run, o genera el APK en
   `Build > Build Bundle(s) / APK(s) > Build APK(s)`.
5. **La primera vez**, toca el botón tú mismo y acepta los permisos (SMS, cámara, micrófono) para que
   ella no tenga que ver esas ventanas.

## Cómo entran los hijos

Les llega el SMS con el enlace `https://meet.jit.si/<ROOM>`. Lo abren con la app **Jitsi Meet**
(gratis en Play Store / App Store) o desde el navegador.

## Importante

- Jitsi **no hace sonar** el teléfono de nadie; el SMS es el aviso. Para un timbre real habría que añadir
  notificaciones push (Firebase).
- El SMS se manda desde la línea del teléfono de ella (puede tener costo según su plan). No se reenvía si
  vuelve a tocar el botón antes de `SMS_COOLDOWN_MINUTES` (5 por defecto).
- Si `CHILDREN_PHONES` está vacío o ella niega el permiso de SMS, la app igual entra a la llamada.
- En el servidor público `meet.jit.si` cualquiera que adivine el nombre de la sala puede entrar. Usa un
  nombre largo y, si quieres más control, un servidor propio o 8x8 JaaS (cambia `SERVER_URL`).
- Algunos servicios públicos limitan el uso del SDK embebido (por ejemplo, tiempo máximo de llamada).
  Prueba una llamada larga antes de dárselo a ella.
- La dependencia `jitsi-meet-sdk:+` toma la última versión. Si Gradle pide un `compileSdk` o un plugin de
  Android más nuevo, actualiza `compileSdk` en `app/build.gradle.kts` y la versión de AGP en
  `build.gradle.kts`.
