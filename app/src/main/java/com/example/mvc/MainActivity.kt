package com.example.mvc

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

    private lateinit var binding : ActivityMainBinding;
    private lateinit var todoRepository: TodoRepository;
    private lateinit var adapter : MainAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo-database"
        ).build();

        todoRepository = TodoRepository(todoDatabase.todoDao());

        binding.addButton.setOnClickListener {
          val task = binding.taskEditText.text.toString();
            if(task.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    insertTodoAndLoad();
                };
            };
        };
        loadTodos();
    };

    private fun loadTodos() {
        CoroutineScope(Dispatchers.IO).launch {
          val todos = todoRepository.getAllTodos();
            withContext(Dispatchers.Main){
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
            withContext(Dispatchers.Main) {
                binding.taskEditText.text.clear();
            };
            loadTodos();
        };
    };


}