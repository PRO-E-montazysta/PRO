package com.example.e_montazysta.ui.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentCreateReleaseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseCreateFragment : Fragment() {
    private val releaseCreateViewModel: ReleaseCreateViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: ReleaseCreateFragmentArgs by navArgs()
        val stageId = args.stageId
        val binding: FragmentCreateReleaseBinding = FragmentCreateReleaseBinding.inflate(inflater, container, false)
        binding.viewModel = releaseCreateViewModel
        return binding.root
    }
}