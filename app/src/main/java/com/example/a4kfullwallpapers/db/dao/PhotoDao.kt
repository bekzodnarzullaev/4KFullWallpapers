package com.example.a4kfullwallpapers.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a4kfullwallpapers.db.entity.PhotoEntity
import com.example.a4kfullwallpapers.models.UnSplashPhoto

@Dao
interface PhotoDao {

    @Insert
    fun insertPhoto(photoEntity: PhotoEntity)

    @Query("DELETE FROM liked_table WHERE photo_id = :photoId ")
    fun deletePhoto(photoId:String)

    @Query("SELECT * FROM liked_table")
    fun getLikedPhotos():LiveData<List<PhotoEntity>>
}