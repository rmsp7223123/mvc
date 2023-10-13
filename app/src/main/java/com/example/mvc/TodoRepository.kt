package com.example.mvc

class TodoRepository(private val todoDao : TodoDao) {
    suspend fun getAllTodos() : List<TodoEntity> {
        return todoDao.getAllTodos();
    }

    suspend fun insertTodo(todoEntity: TodoEntity){
        return todoDao.insertTodo(todoEntity);
    }
}