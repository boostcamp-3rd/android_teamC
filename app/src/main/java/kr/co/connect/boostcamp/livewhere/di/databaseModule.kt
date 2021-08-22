package kr.co.connect.boostcamp.livewhere.di

import androidx.room.Room
import kr.co.connect.boostcamp.livewhere.data.database.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

const val DATABASE_NAME = "mdatabase.db"

val databaseModule = module {
    single("databaseModule") {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single("bookmarkDAO") {
        get<AppDataBase>().bookmarkDao()
    }

    single("recentSearchDAO") {
        get<AppDataBase>().recentSearchDao()
    }
}