package com.vogella.android.roomwordssample

import android.app.Application
import android.content.Context
import android.os.AsyncTask

import androidx.lifecycle.LiveData


class WordRepository constructor(context: Context) {
    var mWordDao: WordDao
    var allWords: LiveData<List<Word>>

    fun insert(word: Word?) {
        insertAsyncTask(mWordDao).execute(word)
    }

    companion object {
        //assíncrona
        class insertAsyncTask constructor(private val mAsyncTaskDao: WordDao) :
            AsyncTask<Word, Void, Boolean>() {

            //onde ele efetivamente faz a rotina assincronamente
            //ou seja, fora da Main Thread
            //teriam outras N formas.. Handler, Coroutines, Anko doAsync
            override fun doInBackground(vararg params: Word): Boolean {
                mAsyncTaskDao.insert(params[0])
                return true
            }

        }
    }

    //é o static do Java
    init {
        val db = WordRoomDatabase.getDatabase(context)
        mWordDao = db!!.wordDao()
        allWords = mWordDao.allWords()
    }
}