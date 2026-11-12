package com.familia.llamada

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import java.net.URL

class MainActivity : AppCompatActivity() {

    // Pide el permiso de SMS la primera vez; haya o no permiso, la llamada siempre arranca.
    private val smsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) sendAlertSms()
            startCall()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tvTitle).text = Config.TITLE
        findViewById<Button>(R.id.btnLlamar).setOnClickListener { onCallPressed() }
    }

    private fun roomUrl(): String = "${Config.SERVER_URL.trimEnd('/')}/${Config.ROOM}"

    /** Único botón: avisa a los hijos por SMS (si hay teléfonos) y entra a la sala. */
    private fun onCallPressed() {
        if (Config.CHILDREN_PHONES.isEmpty()) {
            startCall()
            return
        }
        val hasPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            sendAlertSms()
            startCall()
        } else {
            smsPermission.launch(Manifest.permission.SEND_SMS)
        }
    }

    /**
     * Manda el SMS a cada hijo. Hay un descanso para que, si ella toca el botón
     * varias veces seguidas, los hijos no reciban mensajes repetidos.
     */
    private fun sendAlertSms() {
        val prefs = getSharedPreferences("llamada", Context.MODE_PRIVATE)
        val now = System.currentTimeMillis()
        val last = prefs.getLong("ultimo_aviso", 0L)
        if (now - last < Config.SMS_COOLDOWN_MINUTES * 60_000L) return

        val text = "${Config.ALERT_MESSAGE} ${roomUrl()}"
        try {
            val sms: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                getSystemService(SmsManager::class.java)
            } else {
                @Suppress("DEPRECATION")
                SmsManager.getDefault()
            }
            Config.CHILDREN_PHONES.forEach { number ->
                sms.sendMultipartTextMessage(number, null, sms.divideMessage(text), null, null)
            }
            prefs.edit().putLong("ultimo_aviso", now).apply()
        } catch (e: Exception) {
            // Si el SMS falla, no se bloquea la llamada.
        }
    }

    private fun startCall() {
        val userInfo = JitsiMeetUserInfo(Bundle().apply {
            putString("displayName", Config.MY_NAME)
        })

        val options = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL(Config.SERVER_URL))
            .setRoom(Config.ROOM)
            .setUserInfo(userInfo)
            .setAudioMuted(false)
            .setVideoMuted(false)
            .setWelcomePageEnabled(false)
            // Pantalla lo más simple posible para una persona mayor
            .setFeatureFlag("prejoinpage.enabled", false)
            .setFeatureFlag("chat.enabled", false)
            .setFeatureFlag("invite.enabled", false)
            .setFeatureFlag("add-people.enabled", false)
            .setFeatureFlag("call-integration.enabled", false)
            .setFeatureFlag("pip.enabled", false)
            .setFeatureFlag("raise-hand.enabled", false)
            .setFeatureFlag("reactions.enabled", false)
            .setFeatureFlag("security-options.enabled", false)
            .setFeatureFlag("meeting-name.enabled", false)
            .setFeatureFlag("recording.enabled", false)
            .setFeatureFlag("live-streaming.enabled", false)
            .setFeatureFlag("kick-out.enabled", false)
            .build()

        JitsiMeetActivity.launch(this, options)
    }
}
