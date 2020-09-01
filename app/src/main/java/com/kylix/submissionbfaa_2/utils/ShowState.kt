package com.kylix.submissionbfaa_2.utils

import android.content.res.Resources
import android.view.View
import com.kylix.submissionbfaa_2.R
import com.kylix.submissionbfaa_2.databinding.FollowFragmentBinding
import com.kylix.submissionbfaa_2.databinding.HomeFragmentBinding

class ShowState(private val stateId: Int) {

    fun loading(homeBinding: HomeFragmentBinding?, followBinding: FollowFragmentBinding?){
        when(stateId){
            1 -> {
                homeBinding?.errLayout?.mainNotFound?.visibility = View.GONE
                homeBinding?.progress?.visibility = View.VISIBLE
                homeBinding?.recyclerHome?.visibility = View.GONE
            }
            2 -> {
                followBinding?.errLayout?.mainNotFound?.visibility = View.GONE
                followBinding?.progress?.visibility = View.VISIBLE
                followBinding?.recylerFollow?.visibility = View.GONE
            }
        }
    }

    fun success(homeBinding: HomeFragmentBinding?, followBinding: FollowFragmentBinding?){
        when(stateId){
            1 ->{
                homeBinding?.errLayout?.mainNotFound?.visibility = View.GONE
                homeBinding?.progress?.visibility = View.GONE
                homeBinding?.recyclerHome?.visibility = View.VISIBLE
            }

            2 ->{
                followBinding?.errLayout?.mainNotFound?.visibility = View.GONE
                followBinding?.progress?.visibility = View.GONE
                followBinding?.recylerFollow?.visibility = View.VISIBLE
            }
        }
    }

    fun error(homeBinding: HomeFragmentBinding? ,followBinding: FollowFragmentBinding? ,message: String?, resources: Resources){
        when(stateId){
            1 -> {
                homeBinding?.errLayout?.mainNotFound?.visibility = View.VISIBLE
                homeBinding?.errLayout?.emptyText?.text = message ?: resources.getString(R.string.not_found)
                homeBinding?.progress?.visibility = View.GONE
                homeBinding?.recyclerHome?.visibility = View.GONE
            }
            2 -> {
                followBinding?.errLayout?.mainNotFound?.visibility = View.VISIBLE
                followBinding?.errLayout?.emptyText?.text = message ?: resources.getString(R.string.not_found)
                followBinding?.progress?.visibility = View.GONE
                followBinding?.recylerFollow?.visibility = View.GONE
            }
        }
    }
}