package com.example.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mvc.databinding.ActivityMemoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemoBinding;
    private lateinit var repository: MemoRepository;
    private lateinit var adapter: MemoAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val memoDatabase = Room.databaseBuilder(
            applicationContext,
            MemoDatabase::class.java, "memo-database"
        ).build();

        repository = MemoRepository(memoDatabase.memoDao());

        binding.saveButton.setOnClickListener {
            val memo = binding.memoEditText.text.toString();
            if (memo.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    insertMemoAndLoad();
                };
            };
        }

        loadMemos();
    };


    private fun loadMemos() {
        CoroutineScope(Dispatchers.IO).launch {
            val memos = repository.getAllMemos();
            withContext(Dispatchers.Main) {
                adapter = MemoAdapter(memos);
                binding.recv.layoutManager = LinearLayoutManager(this@MemoActivity);
                binding.recv.adapter = adapter;
            }
        };
    }

    private suspend fun insertMemoAndLoad() {
        val memo = binding.memoEditText.text.toString();
        if (memo.isNotEmpty()) {
            val todo = MemoEntity(0, memo);
            repository.insert(todo);
            withContext(Dispatchers.Main) {// 코루틴 메인스레드에서 실행될 때 사용,  해당 코드는 메인 스레드에서 실행
                binding.memoEditText.text.clear();
            };
            loadMemos();
        };
    };


}