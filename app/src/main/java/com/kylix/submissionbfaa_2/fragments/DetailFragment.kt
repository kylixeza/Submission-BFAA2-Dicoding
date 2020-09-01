package com.kylix.submissionbfaa_2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.kylix.submissionbfaa_2.R
import com.kylix.submissionbfaa_2.databinding.DetailFragmentBinding
import com.kylix.submissionbfaa_2.utils.State
import com.kylix.submissionbfaa_2.viemodel.DetailViewModel



class DetailFragment : Fragment() {

    private lateinit var detailBinding: DetailFragmentBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
        detailViewModel.setDetail(args.Username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailBinding = DetailFragmentBinding.inflate(layoutInflater, container, false)
        detailBinding.lifecycleOwner = viewLifecycleOwner
        observeDetail()
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailBinding.content.transitionName = args.Username

        val tabList = arrayOf(
            resources.getString(R.string.followers),
            resources.getString(R.string.following)
        )
        pagerAdapter = PagerAdapter(tabList, args.Username, this)
        detailBinding.pager.adapter = pagerAdapter

        TabLayoutMediator(detailBinding.tabs, detailBinding.pager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeDetail() {
        detailViewModel.dataDetail.observe(viewLifecycleOwner, Observer {
            if (it.state == State.SUCCESS) {
                detailBinding.data = it.data
            }
        })
    }

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = tabList.size
        override fun createFragment(position: Int): Fragment = FollowFragment.newInstance(username, tabList[position])
    }
}