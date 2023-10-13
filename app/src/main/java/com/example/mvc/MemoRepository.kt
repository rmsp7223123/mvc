package com.example.mvc

import androidx.lifecycle.LiveData

class MemoRepository(private val memoDao : MemoDao) {
    suspend fun getAllMemos() : List<MemoEntity> {
        return memoDao.getAllMemos();
    }

    suspend fun insert(memo: MemoEntity) {
        memoDao.insertMemo(memo);
    };

    suspend fun update(memo: MemoEntity) {
        memoDao.updateMemo(memo);
    };

    suspend fun delete(memo: MemoEntity) {
        memoDao.deleteMemo(memo);
    };
}