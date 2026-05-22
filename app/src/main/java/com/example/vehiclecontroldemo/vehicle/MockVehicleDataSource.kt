package com.example.vehiclecontroldemo.vehicle


import android.os.Handler
import android.os.Looper

class MockVehicleDataSource : VehicleDataSource {
    private val handler = Handler(Looper.getMainLooper())
    private var listener: VehicleDataSource.SpeedListener? = null
    private var fanSpeed = 1

    private val speedRunnable = object : Runnable {
        override fun run() {
            val speed = (0..120).random().toFloat()
            listener?.onSpeedChanged(speed)

            handler.postDelayed(this, 1000)

        }
    }

    override fun connect(listener: VehicleDataSource.SpeedListener) {
        this.listener = listener

        listener.onConnectionStateChanged(ConnectionState.CONNECTING)

        handler.postDelayed({
            listener.onConnectionStateChanged(ConnectionState.CONNECTED)
            handler.post(speedRunnable)
        }, 1000)
    }

    override fun disconnect() {
        handler.removeCallbacks(speedRunnable)
        listener?.onConnectionStateChanged(ConnectionState.DISCONNECTED)
        listener = null
    }

    override fun increaseFan() {
        if (fanSpeed < 5) {
            fanSpeed++
            listener?.onFanSpeedChanged(fanSpeed)
        }
    }

    override fun decreaseFan() {
        if (fanSpeed > 1) {
            fanSpeed--
            listener?.onFanSpeedChanged(fanSpeed)
        }
    }

    override fun simulateError() {
        handler.removeCallbacks(speedRunnable)

        listener?.onConnectionStateChanged(ConnectionState.ERROR)
    }
}