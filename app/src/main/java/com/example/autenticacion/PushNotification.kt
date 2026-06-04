package com.example.autenticacion

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotification : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM", "Nuevo Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "Mensaje recibido")

        remoteMessage.notification?.let {
            Log.d("FCM", "Título: ${it.title}")
            Log.d("FCM", "Mensaje: ${it.body}")
        }
    }
}