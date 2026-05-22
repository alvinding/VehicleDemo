package com.example.vehiclecontroldemo.vehicle

interface VehicleDataSource {
    interface SpeedListener {
        fun onSpeedChanged(speed: Float)
        fun onConnectionStateChanged(state: ConnectionState)
        fun onFanSpeedChanged(speed: Int)
    }

    fun connect(listener: SpeedListener)

    fun disconnect()
    // 控制风量
    fun increaseFan()

    fun decreaseFan()

    // 模拟异常
    fun simulateError()
}