package com.vogella.android.roomwordssample

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

//estudar o Dagger 2
class WordViewModel() : ViewModel() {
    var allWords: LiveData<List<Word>>? = null
    var mRepository: WordRepository? = null

    fun insert(word: Word?) {
        mRepository?.insert(word)
    }

    fun init(context: Context) {
        mRepository = WordRepository(context)
        allWords = mRepository?.allWords
    }
}