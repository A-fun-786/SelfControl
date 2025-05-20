package com.example.myapplication.data

import android.content.Context
import androidx.room.*

abstract class KeywordDatabase: RoomDatabase() {
    abstract fun keywordDao() : KeywordDao

    companion object {
        @Volatile
        private var INSTANCE: KeywordDatabase? = null
        fun getDatabase(context: Context): KeywordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KeywordDatabase::class.java,
                    "keyword_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Entity(tableName = "keyword")
data class Keyword (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var keyword: String
)

@Dao
interface KeywordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keyword: Keyword)

    @Delete
    suspend fun delete(keyword: Keyword)

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<Keyword>

    @Query("SELECT * FROM words WHERE keyword = :keyword")
    suspend fun getWord(keyword: String): Keyword?
}