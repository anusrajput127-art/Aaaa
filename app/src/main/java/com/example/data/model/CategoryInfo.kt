package com.example.data.model

import androidx.compose.ui.graphics.Color

data class CategoryInfo(
  val id: String,
  val nameEn: String,
  val nameUr: String,
  val descriptionEn: String,
  val iconEmoji: String,
  val themeColorHex: Long
) {
  val themeColor: Color
    get() = Color(themeColorHex)
}

object ToyCategories {
  val ALL = CategoryInfo(
    id = "all",
    nameEn = "All Toys",
    nameUr = "تمام کھلونے",
    descriptionEn = "Explore entire toys collection",
    iconEmoji = "🧸",
    themeColorHex = 0xFFFF5252
  )

  val CARS = CategoryInfo(
    id = "cars",
    nameEn = "Toy Cars",
    nameUr = "کھلونا گاڑیاں",
    descriptionEn = "Die-cast, RC & racing vehicles",
    iconEmoji = "🚗",
    themeColorHex = 0xFFE53935
  )

  val DOLLS = CategoryInfo(
    id = "dolls",
    nameEn = "Dolls",
    nameUr = "گڑیا",
    descriptionEn = "Fashion dolls & playsets",
    iconEmoji = "👗",
    themeColorHex = 0xFFD81B60
  )

  val TEDDY_BEARS = CategoryInfo(
    id = "teddies",
    nameEn = "Teddy Bears",
    nameUr = "ٹیڈی بیئر",
    descriptionEn = "Plush cuddle bears & animals",
    iconEmoji = "🧸",
    themeColorHex = 0xFF8D6E63
  )

  val EDUCATIONAL = CategoryInfo(
    id = "educational",
    nameEn = "Educational Toys",
    nameUr = "تعلیمی کھلونے",
    descriptionEn = "STEM kits, puzzles & learning",
    iconEmoji = "🧩",
    themeColorHex = 0xFF0288D1
  )

  val BABY_TOYS = CategoryInfo(
    id = "baby",
    nameEn = "Baby Toys",
    nameUr = "چھوٹے بچوں کے کھلونے",
    descriptionEn = "Rattles, teethers & soft toys",
    iconEmoji = "👶",
    themeColorHex = 0xFF00897B
  )

  val BUILDING_BLOCKS = CategoryInfo(
    id = "blocks",
    nameEn = "Building Blocks",
    nameUr = "تعمیراتی بلاکس",
    descriptionEn = "Construction bricks & magnetic tiles",
    iconEmoji = "🧱",
    themeColorHex = 0xFFFF8F00
  )

  val list = listOf(CARS, DOLLS, TEDDY_BEARS, EDUCATIONAL, BABY_TOYS, BUILDING_BLOCKS)
  val filterList = listOf(ALL) + list
}
