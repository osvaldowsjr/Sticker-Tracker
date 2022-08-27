package com.osvaldo.stickerstracker.ui.addStickers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.AddingFragmentBinding
import com.osvaldo.stickerstracker.ui.cameraScan.CameraActivity
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddingFragment : Fragment(R.layout.adding_fragment), AdapterView.OnItemSelectedListener {
    private val binding: AddingFragmentBinding by viewBinding(AddingFragmentBinding::bind)
    private val addingViewModel: AddingViewModel by viewModel()
    private val listAdapter = AddingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setEndIconVisibility(View.GONE)

        binding.toolbar.setStartIconOnClickListener {
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }

        binding.updateButton.setOnClickListener {
            addingViewModel.updateNation()
        }

        addingViewModel.allNations.observe(viewLifecycleOwner) { listOfNations ->
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                org.koin.android.R.layout.support_simple_spinner_dropdown_item,
                listOfNations
            )
            binding.spinnerNation.apply {
                this.adapter = arrayAdapter
                onItemSelectedListener = this@AddingFragment
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        addingViewModel.allNations.observe(viewLifecycleOwner) {
            listAdapter.submitList(it[position].listOfPlayers)
            binding.imageFlag.setImageResource(it[position].nationFlag)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }
}