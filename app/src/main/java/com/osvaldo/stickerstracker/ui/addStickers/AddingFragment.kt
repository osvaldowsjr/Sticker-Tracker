package com.osvaldo.stickerstracker.ui.addStickers

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.databinding.AddingFragmentBinding
import com.osvaldo.stickerstracker.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddingFragment : Fragment(R.layout.adding_fragment), AdapterView.OnItemSelectedListener {
    private val binding: AddingFragmentBinding by viewBinding(AddingFragmentBinding::bind)
    private val addingViewModel : AddingViewModel by viewModel()
    private val listAdapter = AddingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addingViewModel.allNations.observe(viewLifecycleOwner){
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                org.koin.android.R.layout.support_simple_spinner_dropdown_item,
                it
            )
            binding.spinnerNation.apply {
                this.adapter = arrayAdapter
                onItemSelectedListener = this@AddingFragment
            }
            listAdapter.submitList(it[0].listOfPlayers)
        }

        binding.updateButton.setOnClickListener {
            addingViewModel.allNations.value?.forEach {
                addingViewModel.updateNation(it)
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
        addingViewModel.allNations.observe(viewLifecycleOwner){
            listAdapter.submitList(it[position].listOfPlayers)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }
}