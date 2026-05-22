package com.example.vehiclecontroldemo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vehiclecontroldemo.vehicle.ConnectionState
import com.example.vehiclecontroldemo.vehicle.DrivingMode
import com.example.vehiclecontroldemo.viewmodel.VehicleViewModel
import com.example.vehiclecontroldemo.ui.VehicleStatusFragment
import com.example.vehiclecontroldemo.ui.VehicleControlFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: VehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 防止 旋转屏幕重复 add fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.statusContainer, VehicleStatusFragment())
                .replace(R.id.controlContainer, VehicleControlFragment())
                .commit()
        }
/*
        val tvConnection = findViewById<TextView>(R.id.tvConnection)
        val tvSpeed = findViewById<TextView>(R.id.tvSpeed)
        val tvFan = findViewById<TextView>(R.id.tvFan)

        val btnConnect = findViewById<Button>(R.id.btnConnect)
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)

        val rbParking = findViewById<RadioButton>(R.id.rbParking)
        val rbDriving = findViewById<RadioButton>(R.id.rbDriving)
        val tvRestriction = findViewById<TextView>(R.id.tvRestriction)

        val btnError = findViewById<Button>(R.id.btnError)
// region Observes
        viewModel.connectionState.observe(this) {
            tvConnection.text = "Connection: $it"
        }
        viewModel.speed.observe(this) {
            tvSpeed.text = "Speed: ${it.toInt() } km/h"
        }
        viewModel.fanSpeed.observe(this) {
            tvFan.text = "Fan Speed: $it"
        }
        viewModel.controlsEnabled.observe(this) { enabled ->
            btnPlus.isEnabled = enabled
            btnMinus.isEnabled = enabled

        }
        viewModel.uiMessage.observe(this) { msg ->
            tvRestriction.text = msg
            // 提示文言的显示和隐藏
            tvRestriction.visibility = if (msg.isEmpty()) View.GONE else View.VISIBLE
        }
// endregion
// region Events
        btnConnect.setOnClickListener {
            if (viewModel.connectionState.value == ConnectionState.CONNECTED) {
                viewModel.disconnect()
            } else {
                viewModel.connect()
            }

        }
        btnPlus.setOnClickListener {
            viewModel.increaseFan()
        }
        btnMinus.setOnClickListener {
            viewModel.decreaseFan()
        }

        rbParking.setOnClickListener {
            viewModel.setDrivingMode(DrivingMode.PARKINNG)
        }
        rbDriving.setOnClickListener {
            viewModel.setDrivingMode(DrivingMode.DRIVING)
        }

        btnError.setOnClickListener {
            viewModel.simulateError()
        }

// endregion

 */
    }
}