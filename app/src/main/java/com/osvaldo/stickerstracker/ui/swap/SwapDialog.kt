package com.osvaldo.stickerstracker.ui.swap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.SwapDialogBinding
import com.osvaldo.stickerstracker.ui.customViews.spinner.OnItemSelectedListener
import com.osvaldo.stickerstracker.ui.swap.adapter.PlayerSpinnerAdapter
import com.osvaldo.stickerstracker.utils.setFullScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SwapDialog : DialogFragment(R.layout.swap_dialog) {

    private lateinit var binding: SwapDialogBinding
    private val args: SwapDialogArgs by navArgs()
    private val swapViewModel: SwapViewModel by viewModel()
    private val listener = OnItemSelectedListener()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFullScreen()
        binding = SwapDialogBinding.bind(view)

        swapViewModel.allNations.observe(viewLifecycleOwner) {
            swapViewModel.getRepeatedPlayers()
            setupObservers()
        }

        binding.stickerToReceive.text = args.stickerNumber

        binding.close.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setupObservers() {
        var adapterPosition = 0
        var adapterList = mutableListOf<Player>()
        listener.position.observe(viewLifecycleOwner) { position ->
            adapterPosition = position
        }
        binding.confirm.setOnClickListener {
            swapViewModel.swapStickers(adapterList[adapterPosition].number, args.stickerNumber)
        }
        swapViewModel.repeatedList.observe(viewLifecycleOwner) { list ->
            adapterList = list as MutableList<Player>
            val spinnerAdapter = PlayerSpinnerAdapter(requireContext(), adapterList)
            binding.stickerToGive.apply {
                adapter = spinnerAdapter
                onItemSelectedListener = listener
            }
        }
    }


}