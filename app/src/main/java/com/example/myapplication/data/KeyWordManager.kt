package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.data.Constants.ENTRY_ALREADY_EXISTS
import com.example.myapplication.data.Constants.NO_ENTRY_FOUND
import com.example.myapplication.data.Constants.SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KeyWordManager {
    private lateinit var keywordDao: KeywordDao
    private var msg = ""
    // Initialize the WordDao
    fun init(context: Context) {
        val database = KeywordDatabase.getDatabase(context)
        keywordDao = database.keywordDao()
    }

    fun addOrUpdateWord(word: String): String {

        CoroutineScope(Dispatchers.IO).launch {
            val existingEntry = keywordDao.getWord(word)
            if(existingEntry == null) {
                keywordDao.insert(Keyword(keyword = word))
                msg = SUCCESS
            } else {
                msg = ENTRY_ALREADY_EXISTS
            }
        }
        return msg
    }

    // Function to remove a word
    fun removeWord(word: String): String {
        CoroutineScope(Dispatchers.IO).launch {
            val entryToDelete = keywordDao.getWord(word)
            if (entryToDelete != null) {
                msg = SUCCESS
                keywordDao.delete(entryToDelete)
            }else {
                msg = NO_ENTRY_FOUND
            }
        }
        return msg
    }

    // Function to get all words
    suspend fun getAllWords(): List<Keyword> {
        return withContext(Dispatchers.IO) {
            keywordDao.getAllWords()
        }
    }

    // Function to check if a word exists
    suspend fun containsWord(word: String): Boolean {
        return withContext(Dispatchers.IO) {
            keywordDao.getWord(word) != null
        }
    }
}