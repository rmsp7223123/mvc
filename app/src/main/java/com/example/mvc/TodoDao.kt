package com.example.mvc

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("select * from todos")
    suspend fun getAllTodos() : List<TodoEntity>;

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: TodoEntity);
}