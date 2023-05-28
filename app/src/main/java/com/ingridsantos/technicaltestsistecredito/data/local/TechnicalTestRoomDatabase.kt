package com.ingridsantos.technicaltestsistecredito.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ingridsantos.technicaltestsistecredito.data.local.dao.UserDao
import com.ingridsantos.technicaltestsistecredito.data.local.entities.LocalUser

@Database(
    entities = [
        LocalUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TechnicalTestRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
