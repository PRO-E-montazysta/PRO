package com.example.e_montazysta.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.FragmentUsersBinding
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {
    private val viewModel: UserListViewModel by viewModel()
    private lateinit var binding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        binding = FragmentUsersBinding.inflate(inflater, container, false)

        val adapter = UserListAdapter(UserListAdapter.CustomClickListener { userId ->
            findNavController().navigate(
                UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                    userId
                )
            )
        })

        binding.list.adapter = adapter

        viewModel.getFilterUsers()

        viewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let {
                if (it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

        // Szuflada
        val drawerLayout: DrawerLayout = binding.drawerLayout

        // Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> {
                    drawerLayout.openDrawer(GravityCompat.END)
                    true
                }

                else -> {
                    false
                }
            }
        }
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }


        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }




        binding.lifecycleOwner = this
        return binding.root

    }

}
