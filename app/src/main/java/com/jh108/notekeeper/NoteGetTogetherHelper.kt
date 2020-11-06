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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        locManager.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        locManager.stop()
    }
}