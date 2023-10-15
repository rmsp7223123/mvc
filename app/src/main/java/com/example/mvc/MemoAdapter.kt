package com.example.mvc

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc.databinding.ItemRecv2Binding

class MemoAdapter(private val memos: List<MemoEntity>) :
    RecyclerView.Adapter<MemoAdapter.ViewHolder>() {

    private var selectedPosition: Int? = null;

    inner class ViewHolder(var binding: ItemRecv2Binding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.containerMemo.setOnLongClickListener {
                selectedPosition = adapterPosition
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecv2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        viewHolder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
            menu.add(Menu.NONE, R.id.edit_memo, Menu.NONE, "수정");
            menu.add(Menu.NONE, R.id.delete_memo, Menu.NONE, "삭제");
            menu.add(Menu.NONE, R.id.cancel_memo, Menu.NONE, "취소");
        }
        return viewHolder;
    }

    override fun getItemCount(): Int {
        return memos.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNum.text = memos[position].id.toString();
        holder.binding.tvTodoText.text = memos[position].content;
    };
    fun getSelectedPosition(): Int {
        return selectedPosition!!;
    };
}