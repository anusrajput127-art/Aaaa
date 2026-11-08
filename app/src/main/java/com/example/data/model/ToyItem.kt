package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toys")
data class ToyItem(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val titleEn: String,
  val titleUr: String,
  val categoryId: String, // cars, dolls, teddies, educational, baby, blocks
  val pricePkr: Int,
  val originalPricePkr: Int? = null,
  val stock: Int = 10,
  val descriptionEn: String,
  val descriptionUr: String,
  val ageRange: String = "3+ Years",
  val rating: Float = 4.8f,
  val reviewCount: Int = 25,
  val imageUrl: String = "",
  val isFeatured: Boolean = false,
  val badgeText: String = "",
  val featuresList: String = "High Quality, Child Safe, Durable, Non-toxic"
)
