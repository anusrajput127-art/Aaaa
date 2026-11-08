package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderRecord(
  @PrimaryKey
  val orderId: String,
  val customerName: String,
  val customerPhone: String,
  val city: String,
  val address: String,
  val orderNotes: String = "",
  val paymentMethod: String = "Cash on Delivery (کیش آن ڈیلیوری)",
  val itemsSummary: String,
  val totalItemsCount: Int,
  val subtotalPkr: Int,
  val deliveryFeePkr: Int,
  val discountPkr: Int = 0,
  val totalAmountPkr: Int,
  val status: String = "Pending", // Pending, Confirmed, Shipped, Delivered, Cancelled
  val timestamp: Long = System.currentTimeMillis()
)
