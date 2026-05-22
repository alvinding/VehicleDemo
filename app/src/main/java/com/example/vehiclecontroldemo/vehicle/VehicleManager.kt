package com.example.vehiclecontroldemo.vehicle

import android.os.Handler
import android.os.Looper

class VehicleManager {

    interface SpeedListener {
        fun onSpeedChanged(speed: Float)
    }

    private val handler = Handler(Looper.getMainLooper())
    private var speedListener: SpeedListener? = null

    private val speedRunnable = object : Runnable {
        override fun run() {
            val next = (0..120).random().toFloat()
            speedListener?.onSpeedChanged(next)

            handler.postDelayed(this, 1000)

        }
    }

    fun connect(listener: SpeedListener) {
        speedListener = listener
        handler.post(speedRunnable)
    }

    fun disconnect() {
        handler.removeCallbacks(speedRunnable)
        speedListener = null
    }
}