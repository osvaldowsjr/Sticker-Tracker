package com.osvaldo.stickerstracker.ui.addStickers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.AddingFragmentBinding
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddingFragment : Fragment(R.layout.adding_fragment) {
    private val binding: AddingFragmentBinding by viewBinding(AddingFragmentBinding::bind)
    private val addingViewModel: AddingViewModel by viewModel()
    private val listAdapter = AddingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply {
            setLastIconOnClickListener {
                findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToCameraFragment())
            }
            setLastIconVisibility(View.VISIBLE)
            setLastIconDrawable(R.drawable.icon_camera)
            setBackIconOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        }

        binding.updateButton.setOnClickListener {
            addingViewModel.updateNation()
        }

        binding.spinnerNation.position.observe(viewLifecycleOwner) { position ->
            addingViewModel.allNations.observe(viewLifecycleOwner) {
                listAdapter.submitList(it[position].listOfPlayers)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.playersList.apply {
            this.adapter = listAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}