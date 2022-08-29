package com.osvaldo.stickerstracker.ui.information

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.getFlag
import com.osvaldo.stickerstracker.databinding.InformationFragmentBinding
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment : Fragment(R.layout.information_fragment) {

    private val infoViewModel: InformationViewModel by viewModel()
    private val binding: InformationFragmentBinding by viewBinding(InformationFragmentBinding::bind)

    override fun onStart() {
        super.onStart()
        setupObservers()
    }

    private fun setupObservers() {
        infoViewModel.nationMostObtained.observe(viewLifecycleOwner){
            binding.nationName.text = it.nationName
            binding.nationFlag.setImageResource(it.nationEnum.getFlag())
        }
        infoViewModel.allNations.observe(viewLifecycleOwner) {
            infoViewModel.getAlbumCompletion()
            infoViewModel.getMostObtained()
        }
        infoViewModel.albumCompletion.observe(viewLifecycleOwner) {
            binding.stickerRatio.text =
                getString(R.string.sticker_completion, it.first.toString(), it.second.toString())
            binding.progressBar.max = it.second
            binding.progressBar.progress = it.first
        }
    }
}