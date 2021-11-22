package com.example.a4kfullwallpapers.db.databasemodule

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a4kfullwallpapers.db.dao.PhotoDao
import com.example.a4kfullwallpapers.db.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
abstract class PhotoDB:RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}