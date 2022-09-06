package com.osvaldo.stickerstracker.ui.sharing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Connection
import com.osvaldo.stickerstracker.databinding.ShareFragmentBinding
import com.osvaldo.stickerstracker.ui.customViews.DialogProvider
import com.osvaldo.stickerstracker.ui.customViews.DialogProvider.providesConnectionDialog
import com.osvaldo.stickerstracker.ui.sharing.adapter.EndpointAdapter
import com.osvaldo.stickerstracker.utils.PermissionManager
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.text.Charsets.UTF_8

class SharingFragment : PermissionManager(PermissionsNeeded.NEARBY) {

    val binding: ShareFragmentBinding by viewBinding(ShareFragmentBinding::bind)
    private val strategy = Strategy.P2P_STAR
    private var opponentEndpointId: String? = null
    private val endpointAdapter = EndpointAdapter { name, id ->
        startConnection(name, id)
    }
    private val endpointList = mutableListOf<Connection>()
    private lateinit var connectionsClient: ConnectionsClient
    private val sharingViewModel: SharingViewModel by viewModel()

    override fun isGaranted() {
        // Do nothing
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.share_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionsClient = Nearby.getConnectionsClient(requireActivity())

        binding.enterRoom.setOnClickListener {
            showLoading()
            startDiscovery()
        }

        binding.openRoom.setOnClickListener {
            showRoomName()
            startAdvertising()
        }

        binding.endpoints.apply {
            adapter = endpointAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.toolbar.setBackIconOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showRoomName() {
        binding.roomOpened.apply{
            text = getString(R.string.room_opened, binding.myNick.text.toString())
            visibility = View.VISIBLE
        }
    }

    private fun hideRoomName() {
        binding.roomOpened.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
        DialogProvider.providesExplanationDialog(
            requireContext()
        ) { requireActivity().onBackPressedDispatcher.onBackPressed() }.show()

        sharingViewModel.allNations.observe(viewLifecycleOwner) {
            sharingViewModel.getRepeatedPlayers(it)
        }

        sharingViewModel.listOfPlayers.observe(viewLifecycleOwner) {
        }
    }

    override fun onStop() {
        super.onStop()
        connectionsClient.stopAllEndpoints()
    }

    override fun onPause() {
        super.onPause()
        connectionsClient.stopAllEndpoints()
    }

    private fun sendListMessage() {
        sharingViewModel.allNations.observe(viewLifecycleOwner) {
            sharingViewModel.getRepeatedPlayers(it)
            connectionsClient.sendPayload(
                opponentEndpointId!!,
                Payload.fromBytes(sharingViewModel.getMessageToSend())
            )
        }
    }

    private val payloadCallback: PayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            //receber informacoes e traduzir
            payload.asBytes()?.let {
                findNavController().navigate(
                    SharingFragmentDirections.actionSharingFragmentToCompareFragment(
                        String(it, UTF_8)
                    )
                )
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            if (update.status == PayloadTransferUpdate.Status.SUCCESS)
                Log.d("TESTE", "SUCESSO")
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            hideLoading()
            hideRoomName()
            providesConnectionDialog(
                requireContext(),
                {
                    Nearby.getConnectionsClient(requireActivity())
                        .acceptConnection(endpointId, payloadCallback)
                },
                { Nearby.getConnectionsClient(requireActivity()).rejectConnection(endpointId) },
                info.authenticationDigits
            )
                .show()
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                opponentEndpointId = endpointId
                sendListMessage()
                connectionsClient.stopDiscovery()
                connectionsClient.stopAdvertising()
            }
        }

        override fun onDisconnected(endpointId: String) {
            removeEndpointFromList(endpointId)
        }
    }

    private fun startAdvertising() {
        val options = AdvertisingOptions.Builder().setStrategy(strategy).build()
        connectionsClient.startAdvertising(
            binding.myNick.text.toString(),
            "iAlbum Qatar 2022",
            connectionLifecycleCallback,
            options
        )
    }

    private fun startDiscovery() {
        val options = DiscoveryOptions.Builder().setStrategy(strategy).build()
        connectionsClient.startDiscovery("iAlbum Qatar 2022", endpointDiscoveryCallback, options)
    }

    private fun startConnection(name: String, id: String) {
        showLoading()
        connectionsClient.requestConnection(
            name, id, connectionLifecycleCallback
        )
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            hideLoading()
            endpointList.add(Connection(info.endpointName, endpointId))
            endpointAdapter.submitList(endpointList)
            endpointAdapter.notifyItemInserted(endpointList.lastIndex)
        }

        override fun onEndpointLost(endpointId: String) {
            removeEndpointFromList(endpointId)
        }
    }

    private fun removeEndpointFromList(endpointId: String) {
        for (i in endpointList.indices) {
            if (endpointList[i].endpointId == (endpointId)) {
                endpointList.removeAt(i)
            }
        }
    }
}