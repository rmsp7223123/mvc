package com.example.mvc

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

class EditMemoDialog(context: Context, memo: MemoEntity, onEditListener: (MemoEntity) -> Unit) {
    private val editText = EditText(context).apply {
        setText(memo.content)
    }

    private val dialog = AlertDialog.Builder(context)
        .setTitle("Edit Memo")
        .setView(editText)
        .setPositiveButton("Save") { _, _ ->
            val updatedMemo = MemoEntity(memo.id, editText.text.toString());
            onEditListener.invoke(updatedMemo)
        }
        .setNegativeButton("Cancel", null)
        .create()

    fun show() {
        dialog.show()
    }
}