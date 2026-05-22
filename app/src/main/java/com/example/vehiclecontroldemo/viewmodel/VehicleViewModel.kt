package com.example.vehiclecontroldemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vehiclecontroldemo.vehicle.ConnectionState
import com.example.vehiclecontroldemo.vehicle.DrivingMode
import com.example.vehiclecontroldemo.vehicle.MockVehicleDataSource
import com.example.vehiclecontroldemo.vehicle.VehicleDataSource
import com.example.vehiclecontroldemo.vehicle.VehicleManager



class VehicleViewModel: ViewModel(), VehicleDataSource.SpeedListener {
    private val _connectionState = MutableLiveData(ConnectionState.DISCONNECTED)
    val connectionState: LiveData<ConnectionState> = _connectionState

    private val _speed = MutableLiveData(0f)
    val speed: LiveData<Float> = _speed

    private val _fanSpeed = MutableLiveData(1)
    val fanSpeed: LiveData<Int> = _fanSpeed

    private val _drivingMode = MutableLiveData(DrivingMode.PARKINNG)
    val drivingMode: LiveData<DrivingMode> = _drivingMode

    val controlsEnabled = MediatorLiveData<Boolean>() // 中介 LiveData

    val uiMessage = MediatorLiveData<String>()

    // 先用 mock 数据
    private val vehicleDataSource : VehicleDataSource = MockVehicleDataSource()


    init {
        // 监听多个 LiveData , 变化时通知我
        controlsEnabled.addSource(connectionState) { updateControlsState() }
        controlsEnabled.addSource(drivingMode) { updateControlsState() }
        uiMessage.addSource(connectionState) { updateUiMessage() }
        uiMessage.addSource(drivingMode) { updateUiMessage() }
    }

    fun setDrivingMode(mode: DrivingMode) {
        _drivingMode.value = mode
    }

    private fun updateControlsState() {
        val connected = connectionState.value == ConnectionState.CONNECTED
        val parked = drivingMode.value == DrivingMode.PARKINNG

        // 车机连接中 & 停车
        controlsEnabled.value = connected && parked

    }

    private fun updateUiMessage() {
        when {
            connectionState.value == ConnectionState.ERROR -> {
                uiMessage.value = "Vehicle service unavailable"
            }

            connectionState.value != ConnectionState.CONNECTED -> {
                uiMessage.value = "Vehicle not connected"
            }

            drivingMode.value == DrivingMode.DRIVING -> {
                uiMessage.value = "Controls restricted while driving"
            }

            else -> {
                uiMessage.value = ""
            }
        }
    }

    // 风速+1
    fun increaseFan() {
        vehicleDataSource.increaseFan()
    }
    // 风速-1
    fun decreaseFan() {
        vehicleDataSource.decreaseFan()
    }

    fun connect() {
        vehicleDataSource.connect(this)
    }

    fun disconnect() {
        vehicleDataSource.disconnect()
        _speed.value = 0f
    }

    fun simulateError() {
        vehicleDataSource.simulateError()
    }

    // interface method 传值
    override fun onSpeedChanged(speed: Float) {
        _speed.postValue(speed)
    }

    override fun onConnectionStateChanged(state: ConnectionState) {
//        _connectionState.value = state
        _connectionState.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        vehicleDataSource.disconnect()
    }

    override fun onFanSpeedChanged(speed: Int) {
        _fanSpeed.postValue(speed)
    }

}