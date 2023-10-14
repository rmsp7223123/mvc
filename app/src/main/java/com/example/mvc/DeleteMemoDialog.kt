package com.example.mvc

import android.app.AlertDialog
import android.content.Context

class DeleteMemoDialog(context: Context, onDeleteListener: () -> Unit) {
    private val dialog = AlertDialog.Builder(context)
        .setTitle("Delete Memo")
        .setMessage("삭제 ㅇ")
        .setPositiveButton("Delete") { _, _ ->
            onDeleteListener.invoke()
        }
        .setNegativeButton("Cancel", null)
        .create()

    fun show() {
        dialog.show()
    }
}