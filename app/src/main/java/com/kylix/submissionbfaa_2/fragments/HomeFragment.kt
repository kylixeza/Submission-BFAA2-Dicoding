package com.kylix.submissionbfaa_2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kylix.submissionbfaa_2.R
import com.kylix.submissionbfaa_2.adapter.UserAdapter
import com.kylix.submissionbfaa_2.databinding.HomeFragmentBinding
import com.kylix.submissionbfaa_2.utils.ShowState
import com.kylix.submissionbfaa_2.utils.State
import com.kylix.submissionbfaa_2.viemodel.HomeViewModel


class HomeFragment : Fragment() {

    companion object{
        const val stateHomeId = 1
    }

    private lateinit var homeBinding: HomeFragmentBinding
    private lateinit var homeAdapter: UserAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var showState = ShowState(stateHomeId)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(HomeViewModel::class.java)
        homeBinding.errLayout.emptyText.text = resources.getString(R.string.search_hint)

        homeAdapter = UserAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(HomeFragmentDirections.detailsAction(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }

        homeBinding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }

        homeBinding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.setSearch(query)
                    homeBinding.searchView.clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }
        observeHome()
    }

    private fun observeHome() {
        homeViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.state) {
                    State.SUCCESS -> {
                        resource.data?.let { users ->
                            if (!users.isNullOrEmpty()) {
                                showState.success(homeBinding, null)
                                homeAdapter.setData(users)
                            } else {
                                showState.error(homeBinding,null, null, resources)
                            }
                        }
                    }
                    State.LOADING -> showState.loading(homeBinding, null)
                    State.ERROR -> showState.error(homeBinding, null, it.message, resources
                    )
                }
            }
        })
    }
}