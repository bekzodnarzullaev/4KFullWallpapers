package com.example.a4kfullwallpapers.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class UnSplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnSplashPhotoUrls,
    val user: UnSplashUser
) : Parcelable,Serializable {

    @Parcelize
    data class UnSplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnSplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrls get() = "https://unsplash.com/$username?utm_source=MyImageApp&utm_medium=referral"
    }
}
