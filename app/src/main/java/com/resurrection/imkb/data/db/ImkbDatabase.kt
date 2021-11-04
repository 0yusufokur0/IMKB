package com.resurrection.imkb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.resurrection.imkb.data.db.dao.ImkbDao
import com.resurrection.imkb.data.model.TestItem

@Database(entities = [TestItem::class], version = 1)

abstract class ImkbDatabase : RoomDatabase() {
    abstract fun imkbDao(): ImkbDao
}
