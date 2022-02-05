package com.sanam.nizekinterview.domain.userAcount.model

import android.os.Parcelable
import com.sanam.nizekinterview.common.state.ErrorState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoEns(
    val userName: String,
    var fullName: String? = null,
    val password: String,
): Parcelable





