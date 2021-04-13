package net.syntessense.app.myapplication.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoEntity::class), version = 1)
abstract class TodoDB : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}