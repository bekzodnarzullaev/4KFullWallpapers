package com.example.a4kfullwallpapers.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.a4kfullwallpapers.api.UnSplashApi
import com.example.a4kfullwallpapers.db.dao.PhotoDao
import com.example.a4kfullwallpapers.db.entity.PhotoEntity
import com.example.a4kfullwallpapers.models.UnSplashPhoto
import com.example.a4kfullwallpapers.paging.UnSplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnSplashRepository @Inject constructor(private val unSplashApi: UnSplashApi,private val photoDao: PhotoDao) {

    fun getSearchResult(query:String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnSplashPagingSource(unSplashApi,query) }
        ).liveData

    fun insertPhoto(photoEntity: PhotoEntity){
        photoDao.insertPhoto(photoEntity)
    }

    fun deletePhoto(photoId:String){
        photoDao.deletePhoto(photoId)
    }

    fun getLikedPhotos():LiveData<List<PhotoEntity>>{
        return photoDao.getLikedPhotos()
    }
}