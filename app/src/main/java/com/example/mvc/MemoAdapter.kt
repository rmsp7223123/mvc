package com.example.mvc

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc.databinding.ItemRecv2Binding

class MemoAdapter(private val memos: List<MemoEntity>,private val context : Context) :
    RecyclerView.Adapter<MemoAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemRecv2Binding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecv2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        );
    }

    override fun getItemCount(): Int {
        return memos.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNum.text = memos[position].id.toString();
        holder.binding.tvTodoText.text = memos[position].content;
        holder.binding.containerMemo.setOnClickListener {
            Toast.makeText(context, "토스트 메세지 띄우기 입니다.", Toast.LENGTH_SHORT).show();
        };
    };
}