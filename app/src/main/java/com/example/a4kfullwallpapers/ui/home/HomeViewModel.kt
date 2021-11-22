package com.example.a4kfullwallpapers.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.a4kfullwallpapers.db.entity.PhotoEntity
import com.example.a4kfullwallpapers.models.UnSplashPhoto
import com.example.a4kfullwallpapers.repository.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UnSplashRepository,
    state: SavedStateHandle
) :
    ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    companion object {
        private const val DEFAULT_QUERY = "all"
        private const val CURRENT_QUERY = "current_query"
    }

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    fun likePhoto(photoEntity: PhotoEntity){
        repository.insertPhoto(photoEntity)
    }

    fun unlikePhoto(photoId:String){
        repository.deletePhoto(photoId)
    }

    fun getLikedPhotos():LiveData<List<PhotoEntity>>{
        return repository.getLikedPhotos()
    }
}