package com.noministic.userslogintest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noministic.secupaycodingtest.R
import com.noministic.secupaycodingtest.databinding.FragmentDelayCounterLayoutBinding
import com.noministic.secupaycodingtest.viewmodels.FragmentDelayCounterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDelayCounter : Fragment() {
    private val viewModel: FragmentDelayCounterViewModel by viewModels()
    private lateinit var binding: FragmentDelayCounterLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDelayCounterLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun handleViewsClick() {
        binding.buttonGetDelayCounter.setOnClickListener {
            viewModel.getDelayCounterResponse()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        handleViewsClick()
        viewModel.getCurrentCounter()
    }

    private fun observeViewModel() {
        viewModel.delayCounterResponse.observe(viewLifecycleOwner, {
            /**
             * just converting the object to string to show it to user...it can be improved to
             * show data to the user like files,data,args etc but right now response is empty so just
             * converting it to string
             */
            binding.textviewDelayCounterResponse.text = it.toString()
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            binding.textviewDelayCounterResponse.text = it
        })
        viewModel.currentCounter.observe(viewLifecycleOwner, {
            binding.textviewCurrentCounter.text = getString(R.string.current_counter).plus(it.toString())
        })
    }
}