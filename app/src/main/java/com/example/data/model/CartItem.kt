package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
  @PrimaryKey(autoGenerate = true)
  val cartId: Long = 0,
  val toyId: Long,
  val titleEn: String,
  val titleUr: String,
  val categoryId: String,
  val pricePkr: Int,
  val quantity: Int = 1,
  val stock: Int = 10,
  val imageUrl: String = ""
)
