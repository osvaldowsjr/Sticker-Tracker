package com.osvaldo.stickerstracker.ui.information

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.InformationFragmentBinding
import com.osvaldo.stickerstracker.ui.information.adapter.ObtainedAdapter
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment : Fragment(R.layout.information_fragment) {

    private val infoViewModel: InformationViewModel by viewModel()
    private val binding: InformationFragmentBinding by viewBinding(InformationFragmentBinding::bind)

    override fun onStart() {
        super.onStart()
        setupObservers()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setBackIconOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            setLastIconVisibility(View.GONE)
            setFirstIconVisibility(View.GONE)
        }
    }

    private fun setupObservers() {
        infoViewModel.nationMostObtained.observe(viewLifecycleOwner) {
            val obtainedAdapter = ObtainedAdapter()
            obtainedAdapter.submitList(it)
            if (it.size > 1)
                binding.nationMostObtained.text = getString(R.string.plural_nation)
            binding.mostObtainedNations.apply {
                adapter = obtainedAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        infoViewModel.nationLeastObtained.observe(viewLifecycleOwner) {
            val obtainedAdapter = ObtainedAdapter()
            obtainedAdapter.submitList(it)
            if (it.size > 1)
                binding.nationLeastObtained.text = getString(R.string.plural_nation_least)
            binding.leastObtainedNations.apply {
                adapter = obtainedAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        infoViewModel.allNations.observe(viewLifecycleOwner) {
            infoViewModel.getAlbumCompletion()
            infoViewModel.getMostObtained()
            infoViewModel.getLeastObtained()
        }
        infoViewModel.albumCompletion.observe(viewLifecycleOwner) {
            binding.stickerRatio.text =
                getString(R.string.sticker_completion, it.first.toString(), it.second.toString())
            binding.progressBar.max = it.second
            binding.progressBar.progress = it.first

            binding.percentCompletion.text =
                getString(R.string.percentage,infoViewModel.providePercentage(it))

        }
    }
}