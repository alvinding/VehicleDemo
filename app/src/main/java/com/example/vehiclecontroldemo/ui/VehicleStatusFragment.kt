package com.example.vehiclecontroldemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.vehiclecontroldemo.R
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.vehiclecontroldemo.viewmodel.VehicleViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [VehicleStatusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehicleStatusFragment : Fragment(R.layout.fragment_vehicle_status) {

    private val viewModel: VehicleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvConnection = view.findViewById<TextView>(R.id.tvConnection)
        val tvSpeed = view.findViewById<TextView>(R.id.tvSpeed)


        val tvRestriction = view.findViewById<TextView>(R.id.tvRestriction)

        viewModel.connectionState.observe(viewLifecycleOwner) {
            tvConnection.text = "Connection: $it"
        }
        viewModel.speed.observe(viewLifecycleOwner) {
            tvSpeed.text = "Speed: ${it.toInt() } km/h"
        }

        viewModel.uiMessage.observe(viewLifecycleOwner) { msg ->
            tvRestriction.text = msg
            // 提示文言的显示和隐藏
            tvRestriction.visibility = if (msg.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}