package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ToyItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ToyDao {
  @Query("SELECT * FROM toys ORDER BY id DESC")
  fun getAllToys(): Flow<List<ToyItem>>

  @Query("SELECT * FROM toys WHERE categoryId = :categoryId ORDER BY id DESC")
  fun getToysByCategory(categoryId: String): Flow<List<ToyItem>>

  @Query("SELECT * FROM toys WHERE isFeatured = 1 ORDER BY id DESC")
  fun getFeaturedToys(): Flow<List<ToyItem>>

  @Query("SELECT * FROM toys WHERE id = :toyId LIMIT 1")
  suspend fun getToyById(toyId: Long): ToyItem?

  @Query("SELECT * FROM toys WHERE id = :toyId LIMIT 1")
  fun observeToyById(toyId: Long): Flow<ToyItem?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertToy(toy: ToyItem): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertToys(toys: List<ToyItem>)

  @Update
  suspend fun updateToy(toy: ToyItem)

  @Query("UPDATE toys SET stock = :newStock WHERE id = :toyId")
  suspend fun updateStock(toyId: Long, newStock: Int)

  @Delete
  suspend fun deleteToy(toy: ToyItem)

  @Query("DELETE FROM toys WHERE id = :toyId")
  suspend fun deleteToyById(toyId: Long)

  @Query("DELETE FROM toys")
  suspend fun deleteAllToys()

  @Query("SELECT COUNT(*) FROM toys")
  suspend fun getToyCount(): Int
}
