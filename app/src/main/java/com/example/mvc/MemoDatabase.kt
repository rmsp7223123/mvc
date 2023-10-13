package com.example.mvc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoEntity::class], version = 1, exportSchema = false)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao() : MemoDao;
   // companion object { // Companion object는 Kotlin에서 클래스 내에서 정적 메서드와 변수를 정의하는 방법
        // Companion object 내에 정의된 메서드와 변수는 클래스의 인스턴스 생성 없이 직접 접근할 수 있음
     //   @Volatile // 이 변수가 여러 스레드에서 안전하게 접근됨을 보장
    //    private var INSTANCE: MemoDatabase? = null;

    //    fun getDatabase(context: Context): MemoDatabase {
     //       return INSTANCE ?: synchronized(this) { // synchronized(this) > 여러 스레드가 동시에 접근하지 못하도록 해당 코드 블록을 동기화
     //           val instance = Room.databaseBuilder(
      //              context.applicationContext,
       //             MemoDatabase::class.java,
      //              "memo_database"
       //         ).build();
       //         INSTANCE = instance;
       //         instance;
       //     };
     //   };
   // };
}