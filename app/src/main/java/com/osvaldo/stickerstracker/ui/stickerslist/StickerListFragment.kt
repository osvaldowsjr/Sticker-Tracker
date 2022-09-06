package com.osvaldo.stickerstracker.ui.stickerslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.StickerListFragmentBinding
import com.osvaldo.stickerstracker.ui.main.MainViewModel
import com.osvaldo.stickerstracker.ui.stickerslist.adapter.nations.NationsAdapter
import com.osvaldo.stickerstracker.utils.camera.MarginItemDecoration
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StickerListFragment : Fragment(R.layout.sticker_list_fragment) {

    private val mainViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(StickerListFragmentBinding::bind)
    private val listAdapter = NationsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        binding.nationRecyclerView.apply {
            this.adapter = listAdapter
            addItemDecoration(MarginItemDecoration(1))
            layoutManager = LinearLayoutManager(requireContext())
        }
        mainViewModel.allNations.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setFirstIconDrawable(R.drawable.icon_info)
            setFirstIconVisibility(View.VISIBLE)
            setLastIconVisibility(View.VISIBLE)
            setFirstIconListener {
                findNavController().navigate(
                    StickerListFragmentDirections.actionMainFragmentToInformationFragment()
                )
            }
            setBackIconDrawable(R.drawable.icon_filter)
            setBackIconOnClickListener {
                findNavController().navigate(
                    StickerListFragmentDirections.actionMainFragmentToFilterFragment()
                )
            }
            setLastIconOnClickListener {
                findNavController().navigate(
                    StickerListFragmentDirections.actionMainFragmentToAddingFragment()
                )
            }
        }
    }
}