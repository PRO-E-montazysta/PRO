package com.example.e_montaysta.ui.fragments.qrscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_montaysta.databinding.FragmentQrscanBinding
import com.example.e_montaysta.ui.viewmodels.QRScanViewModel

class QRScanFragment : Fragment() {

    private var _binding: FragmentQrscanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val QRScanViewModel =
                ViewModelProvider(this).get(QRScanViewModel::class.java)

        _binding = FragmentQrscanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textQrscan
        QRScanViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}