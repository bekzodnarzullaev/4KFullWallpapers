package com.example.a4kfullwallpapers.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a4kfullwallpapers.models.UnSplashPhoto
import java.io.Serializable

@Entity(tableName = "liked_table")
data class PhotoEntity(

    @PrimaryKey
    val photo_id: String,
    val url:String,
    val creator:String

)