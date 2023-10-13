package com.example.mvc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc.databinding.ItemRecvBinding

class MainAdapter(private val todos : List<TodoEntity>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(var binding : ItemRecvBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecvBinding.inflate(LayoutInflater.from(parent.context), parent, false));
    }

    override fun getItemCount(): Int {
        return todos.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNum.text = todos[position].id.toString();
        holder.binding.tvTodoText.text = todos[position].task;
    };
}