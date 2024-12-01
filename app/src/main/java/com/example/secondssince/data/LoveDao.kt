package com.example.secondssince.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.secondssince.model.Love
import kotlinx.coroutines.flow.Flow

@Dao
interface LoveDao {
    @Query(
        """
            SELECT * FROM Love
        """
    )
    suspend fun getAllLoves(): List<Love>

    @Query(
        """
            SELECT * FROM Love
        """
    )
    fun getAllLovesFlow(): Flow<List<Love>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLove(love: Love)
}