package com.vogella.android.roomwordssample

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        var INSTANCE: WordRoomDatabase? = null
        fun getDatabase(context: Context): WordRoomDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    WordRoomDatabase::class.java,
                    "word_database"
                )   .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallback)
                    .build()
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

    }


    class PopulateDbAsync(db: WordRoomDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val mDao: WordDao
        var words = arrayOf("dolphin", "crocodile", "cobra")

        //doINBaxckground é em outra thread
        override fun doInBackground(vararg p0: Void?): Void? {
            mDao.deleteAll()
            for (i in 0..words.size - 1) {
                val word = Word(words[i])
                mDao.insert(word)
            }
            return null
        }

        init {
            mDao = db!!.wordDao()
        }
    }
}