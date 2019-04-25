package com.demo.productsmarket.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.demo.productsmarket.data.ImageConverter
import com.demo.productsmarket.data.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(ImageConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "AppDatabase.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}