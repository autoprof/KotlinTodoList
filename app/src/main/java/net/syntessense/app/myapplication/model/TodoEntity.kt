package net.syntessense.app.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "content") var content: String
)
