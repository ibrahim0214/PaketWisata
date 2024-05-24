package com.example.paketwisata.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paketwisata.model.Paket
import okhttp3.internal.Version

@Database(entities = [Paket::class] /* List model Ex:NoteModel */, version = 1,)
abstract class MyDatabase : RoomDatabase() {
    abstract fun daoKeranjang(): DaoKeranjang

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "MyDatabase877" // Database Name
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}