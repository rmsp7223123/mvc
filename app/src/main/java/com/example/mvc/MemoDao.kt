package com.example.mvc

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemoDao {
    @Insert
    suspend fun insertMemo(memo: MemoEntity);

    @Update
    suspend fun updateMemo(memo: MemoEntity);

    @Delete
    suspend fun deleteMemo(memo: MemoEntity);

    @Query("SELECT * FROM memos")
    fun getAllMemos(): List<MemoEntity>;
}