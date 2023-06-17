package com.example.e_montazysta.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentUserDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment() {
    private val viewModel: UserDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val args: UserDetailFragmentArgs by navArgs()
        val userId = args.userId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentUserDetailBinding =
            FragmentUserDetailBinding.inflate(inflater, container, false)

        viewModel.getUserDetail(userId)

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            binding.userFirstNameValue.text = user.firstName
            binding.userLastNameValue.text = user.lastName
            binding.userPeselValue.text = user.pesel
            binding.userRoleValue.text = user.roles.joinToString(", ") { it.value }
            binding.userEmailValue.text = user.email
            binding.userPhoneValue.text = user.phone
        })

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}