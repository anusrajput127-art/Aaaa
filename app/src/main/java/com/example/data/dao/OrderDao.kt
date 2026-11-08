package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.OrderRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
  @Query("SELECT * FROM orders ORDER BY timestamp DESC")
  fun getAllOrders(): Flow<List<OrderRecord>>

  @Query("SELECT * FROM orders WHERE orderId = :orderId LIMIT 1")
  suspend fun getOrderById(orderId: String): OrderRecord?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrder(order: OrderRecord)

  @Update
  suspend fun updateOrder(order: OrderRecord)

  @Query("UPDATE orders SET status = :newStatus WHERE orderId = :orderId")
  suspend fun updateOrderStatus(orderId: String, newStatus: String)

  @Query("SELECT COUNT(*) FROM orders")
  fun getOrderCount(): Flow<Int>

  @Query("SELECT SUM(totalAmountPkr) FROM orders WHERE status != 'Cancelled'")
  fun getTotalRevenue(): Flow<Int?>
}
