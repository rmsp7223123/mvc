package com.example.mvc

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val task: String,
)