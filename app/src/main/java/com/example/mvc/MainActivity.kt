package com.example.mvc

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mvc.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private lateinit var todoRepository: TodoRepository;
    private lateinit var adapter: MainAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo-database"
        ).build();

        todoRepository = TodoRepository(todoDatabase.todoDao());

        binding.btnMove.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MemoActivity::class.java
                )
            );
        };

        binding.addButton.setOnClickListener {
            val task = binding.taskEditText.text.toString();
            if (task.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch { // IO작업을 하기 위한 디스패처를 지정 . 시작
                    insertTodoAndLoad();
                };
            };
        };
        loadTodos();
    };

    private fun loadTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            val todos = todoRepository.getAllTodos();
            withContext(Dispatchers.Main) {
                adapter = MainAdapter(todos);
                binding.todoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity);
                binding.todoRecyclerView.adapter = adapter;
            }
        };
    }

    private suspend fun insertTodoAndLoad() {
        val task = binding.taskEditText.text.toString();
        if (task.isNotEmpty()) {
            val todo = TodoEntity(0, task);
            todoRepository.insertTodo(todo);
            withContext(Dispatchers.Main) {// 코루틴 메인스레드에서 실행될 때 사용,  해당 코드는 메인 스레드에서 실행
                binding.taskEditText.text.clear();
            };
            loadTodos();
        };
    };


}