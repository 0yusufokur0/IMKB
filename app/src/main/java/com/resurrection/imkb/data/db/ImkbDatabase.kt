package com.resurrection.imkb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.resurrection.imkb.data.db.dao.ImkbDao
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.Stock

@Database(entities = [HandshakeResponse::class,Stock::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class ImkbDatabase : RoomDatabase() {
    abstract fun imkbDao(): ImkbDao
}
