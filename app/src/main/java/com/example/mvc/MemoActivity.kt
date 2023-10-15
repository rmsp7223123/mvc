package com.example.mvc

import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        registerForContextMenu(binding.recv);

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
            val memo2 = MemoEntity(0, memo);
            repository.insert(memo2);
            withContext(Dispatchers.Main) {// 코루틴 메인스레드에서 실행될 때 사용,  해당 코드는 메인 스레드에서 실행
                binding.memoEditText.text.clear();
            };
            loadMemos();
        };
    };

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getSelectedPosition();
        val selectedMemo = adapter.memos[position];
        val builder = AlertDialog.Builder(this);
        return when (item.itemId) {
            R.id.edit_memo -> {
                val dialogView = layoutInflater.inflate(R.layout.edit_memo_dialog, null);
                val editText = dialogView.findViewById<EditText>(R.id.editText);
                editText.hint = selectedMemo.content;
                builder.setView(dialogView).setTitle("메모 수정").setPositiveButton("저장") { _, _ ->
                        val updatedMemo = editText.text.toString();
                        CoroutineScope(Dispatchers.IO).launch {
                            selectedMemo.content = updatedMemo;
                            repository.update(selectedMemo);
                            loadMemos();
                        };
                    }.setNegativeButton("취소") { dialog, _ -> dialog.dismiss(); }.show();
                true;
            };
            R.id.delete_memo -> {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(selectedMemo);
                    loadMemos();
                };
                true;
            };
            R.id.cancel_memo -> {
                Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
                true;
            };
            else -> super.onContextItemSelected(item);
        };
    };

};