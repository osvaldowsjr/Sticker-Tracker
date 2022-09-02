package com.osvaldo.stickerstracker.ui.sharing

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.ShareFragmentBinding
import com.osvaldo.stickerstracker.ui.customViews.PermissionAlerter
import com.osvaldo.stickerstracker.utils.viewBinding
import kotlin.text.Charsets.UTF_8


class SharingFragment : Fragment(R.layout.share_fragment) {

    val binding: ShareFragmentBinding by viewBinding(ShareFragmentBinding::bind)

    companion object {
        @RequiresApi(Build.VERSION_CODES.S)
        private val REQUIRED_PERMISSIONS_12UP = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    val requester =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            map.forEach {
                val permission = it.key
                val isGaranted = it.value
                when {
                    isGaranted -> {

                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        permission
                    ) -> {
                        PermissionAlerter.providesAlertDialog(
                            requireContext()
                        ) { requestPermission() }
                            .show()
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Falha", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requester.launch(REQUIRED_PERMISSIONS_12UP)
        } else {
            requester.launch(REQUIRED_PERMISSIONS)
        }
    }

    private val STRATEGY = Strategy.P2P_POINT_TO_POINT
    private var opponentEndpointId: String? = null
    private lateinit var connectionsClient: ConnectionsClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionsClient = Nearby.getConnectionsClient(requireContext())

        binding.discover.setOnClickListener {
            startAdvertising()
            startDiscovery()
        }

        binding.disconect.setOnClickListener {
            opponentEndpointId?.let { connectionsClient.disconnectFromEndpoint(it) }
        }

        binding.send.setOnClickListener {
            sendHello()
        }

    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requester.launch(REQUIRED_PERMISSIONS_12UP)
        } else {
            requester.launch(REQUIRED_PERMISSIONS)
        }
    }

    fun sendHello() {
        connectionsClient.sendPayload(
            opponentEndpointId!!,
            Payload.fromBytes(binding.myNick.text.toString().toByteArray(UTF_8))
        )
    }

    private val payloadCallback: PayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            payload.asBytes()?.let {
                Toast.makeText(requireContext(), String(it, UTF_8), Toast.LENGTH_LONG).show()
            }

        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            if (update.status == PayloadTransferUpdate.Status.SUCCESS)
                Log.d("TESTE", "SUCESSO")
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            //LUGAR para gerir conexoes
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                connectionsClient.stopAdvertising()
                connectionsClient.stopDiscovery()
                opponentEndpointId = endpointId
            }
        }

        override fun onDisconnected(endpointId: String) {

        }
    }

    private fun startAdvertising() {
        val options = AdvertisingOptions.Builder().setStrategy(STRATEGY).build()
        // Note: Advertising may fail. To keep this demo simple, we don't handle failures.
        connectionsClient.startAdvertising(
            binding.myNick.text.toString(),
            "iAlbum Qatar 2022",
            connectionLifecycleCallback,
            options
        )
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            binding.possibleEndPoints.text = endpointId
            //connectionsClient.requestConnection(
            //    binding.myNick.text.toString(),
            //    endpointId,
            //    connectionLifecycleCallback
            //)
        }

        override fun onEndpointLost(endpointId: String) {
        }
    }

    private fun startDiscovery() {
        val options = DiscoveryOptions.Builder().setStrategy(STRATEGY).build()
        connectionsClient.startDiscovery("iAlbum Qatar 2022", endpointDiscoveryCallback, options)
    }
}