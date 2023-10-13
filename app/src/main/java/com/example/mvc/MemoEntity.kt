package com.example.mvc

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String
)