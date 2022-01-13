package com.noministic.userslogintest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noministic.secupaycodingtest.databinding.FragmentStatusCodeLayoutBinding
import com.noministic.secupaycodingtest.viewmodels.FragmentStatusCodeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentStatusCodes : Fragment() {
    private val viewModel: FragmentStatusCodeViewModel by viewModels()
    private lateinit var binding: FragmentStatusCodeLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusCodeLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun handleViewsClick() {
        binding.buttonGetStatusCode.setOnClickListener {
            viewModel.getRandomStatusCode()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        handleViewsClick()
    }

    private fun observeViewModel() {
        viewModel.statusCodeResponse.observe(viewLifecycleOwner, {
            if (it.isNotEmpty())
                binding.textviewStatusResponse.text = it
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}