package com.osvaldo.stickerstracker.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.FilterFragmentBinding
import com.osvaldo.stickerstracker.ui.stickerslist.adapter.stickers.StickersAdapter
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : Fragment(R.layout.filter_fragment) {

    val binding: FilterFragmentBinding by viewBinding(FilterFragmentBinding::bind)
    private val filterViewModel: FilterViewModel by viewModel()
    private val stickersAdapter = StickersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        filterViewModel.allNations.observe(viewLifecycleOwner) {
            setupObservers()
            binding.listPlayers.apply {
                adapter = stickersAdapter
                layoutManager = GridLayoutManager(requireContext(), 5)
            }
        }
    }

    private fun setupToolbar() = with(binding) {
        toolbar.apply {
            setBackIconOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            setLastIconOnClickListener {
                findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSharingFragment())
            }
            setLastIconVisibility(View.VISIBLE)
            setLastIconDrawable(R.drawable.icon_share)
        }
    }

    private fun setupObservers() {
        filterViewModel.listOfPlayers.observe(viewLifecycleOwner) {
            stickersAdapter.submitList(it)
        }
        binding.radioGroup.setOnCheckedChangeListener { group, _ ->
            when (group.checkedRadioButtonId) {
                R.id.repeated -> {
                    filterViewModel.getRepeatedPlayers()
                }
                R.id.missing -> {
                    filterViewModel.getMissingPlayers()
                }
            }
        }
    }
}
