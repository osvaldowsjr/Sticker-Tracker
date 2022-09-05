package com.osvaldo.stickerstracker.ui.swap

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.SwapFragmentBinding
import com.osvaldo.stickerstracker.ui.swap.adapter.SwapAdapter
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.Type


class SwapFragment : Fragment(R.layout.swap_fragment) {

    val binding: SwapFragmentBinding by viewBinding(SwapFragmentBinding::bind)
    val args: SwapFragmentArgs by navArgs()
    val swapViewModel: SwapViewModel by viewModel()
    private val swapAdapter = SwapAdapter { id -> showDialog(id) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gson = Gson()
        val collectionType: Type = object : TypeToken<Collection<Player?>?>() {}.type
        val enums: Collection<Player> = gson.fromJson(args.friendList, collectionType)
        swapViewModel.startFriendList(enums.toMutableList())
        swapViewModel.allNations.observe(viewLifecycleOwner) {
            Log.d("TAG", it[0].nationName)
            setupObservers()
            binding.listOfSwaps.apply {
                adapter = swapAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun showDialog(stickerNumber: String) {
        findNavController().navigate(
            SwapFragmentDirections.actionCompareFragmentToSwapDialog(
                stickerNumber
            )
        )
    }

    private fun setupObservers() {
        swapViewModel.resultList.observe(viewLifecycleOwner) {
            swapAdapter.submitList(it)
        }
        swapViewModel.getMissingPlayers()
    }
}