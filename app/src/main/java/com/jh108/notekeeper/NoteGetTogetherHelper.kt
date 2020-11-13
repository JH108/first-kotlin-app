package com.jh108.notekeeper

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.util.Log

class NoteGetTogetherHelper(val context: Context, val lifecycle: Lifecycle) : LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }

    val tag = this::class.simpleName
    var currentLat = 0.0
    var currentLon = 0.0

    val locManager = PseudoLocationManager(context) { lat, lon ->
        currentLon = lon
        currentLat = lat
        Log.d(tag, "Location Callback Lat:${lat}, Lon:${lon}")
    }

    val msgManager = PseudoMessagingManager(context)
    var msgConnection: PseudoMessagingConnection? = null

    fun sendMessage(note: NoteInfo) {
        val getTogetherMessage = "$currentLat|$currentLon|${note.title}|${note.course?.title}"
        msgConnection?.send(getTogetherMessage)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun connectMsgManager() {
        Log.d(tag, "onCreate")
        msgManager.connect { connection ->
            Log.d(tag, "Connection Callback")
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                msgConnection = connection
            else
                connection.disconnect()

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        Log.d(tag, "startHandler")
        locManager.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        Log.d(tag, "stopHandler")
        locManager.stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disconnectMsgManager() {
        Log.d(tag, "Disconnect")
        msgConnection?.disconnect()
    }
}