package com.example.vehiclecontroldemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.vehiclecontroldemo.R
import com.example.vehiclecontroldemo.vehicle.ConnectionState
import com.example.vehiclecontroldemo.vehicle.DrivingMode
import com.example.vehiclecontroldemo.viewmodel.VehicleViewModel



/**
 * A simple [Fragment] subclass.
 * Use the [VehicleControlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehicleControlFragment : Fragment(R.layout.fragment_vehicle_control) { // 指定 layout

    // 共享 viewModel
    private val viewModel: VehicleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnConnect = view.findViewById<Button>(R.id.btnConnect)
        val tvFan = view.findViewById<TextView>(R.id.tvFan)
        val btnPlus = view.findViewById<Button>(R.id.btnPlus)
        val btnMinus = view.findViewById<Button>(R.id.btnMinus)

        val rbParking = view.findViewById<RadioButton>(R.id.rbParking)
        val rbDriving = view.findViewById<RadioButton>(R.id.rbDriving)

        val btnError = view.findViewById<Button>(R.id.btnError)


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
            viewModel.setDrivingMode(DrivingMode.PARKING)
        }
        rbDriving.setOnClickListener {
            viewModel.setDrivingMode(DrivingMode.DRIVING)
        }

        btnError.setOnClickListener {
            viewModel.simulateError()
        }

        viewModel.fanSpeed.observe(viewLifecycleOwner) {
            tvFan.text = "Fan Speed: $it"
        }
        viewModel.controlsEnabled.observe(viewLifecycleOwner) { enabled ->
            btnPlus.isEnabled = enabled
            btnMinus.isEnabled = enabled

        }
        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            btnConnect.text =
                if (state == ConnectionState.CONNECTED) {
                    "Disconnect"
                } else {
                    "Connect"
                }

            // 连接按钮，连接中不可点击
            btnConnect.isEnabled = state != ConnectionState.CONNECTING
        }
    }
}