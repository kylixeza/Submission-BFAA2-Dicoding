package com.kylix.submissionbfaa_2.networking

import android.os.Parcelable
import com.kylix.submissionbfaa_2.model.GithubUser
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchRespond(
    val total_count : String,
    val incomplete_results: Boolean? = null,
    val items : List<GithubUser>
): Parcelable