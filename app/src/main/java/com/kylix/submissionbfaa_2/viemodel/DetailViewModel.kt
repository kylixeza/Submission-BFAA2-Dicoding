package com.kylix.submissionbfaa_2.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kylix.submissionbfaa_2.model.GithubUser
import com.kylix.submissionbfaa_2.networking.UserRetrofit
import com.kylix.submissionbfaa_2.utils.Resource

class DetailViewModel : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    val dataDetail: LiveData<Resource<GithubUser>> = Transformations
        .switchMap(username) {
            UserRetrofit.getDetailUser(it)
        }

    fun setDetail(userid: String) {
        if (username.value == userid) {
            return
        }
        username.value = userid
    }
}