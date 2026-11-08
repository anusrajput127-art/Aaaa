package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ToyCategories
import com.example.data.model.ToyItem
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToyYellowTertiary

@Composable
fun ProductDetailScreen(
  toy: ToyItem,
  urduProminent: Boolean,
  onBack: () -> Unit,
  onAddToCart: (ToyItem, Int) -> Unit,
  onBuyNow: (ToyItem, Int) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  var quantity by remember { mutableIntStateOf(1) }
  val category = ToyCategories.list.find { it.id == toy.categoryId } ?: ToyCategories.ALL
  val isOutOfStock = toy.stock <= 0

  Scaffold(
    topBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("detail_back_btn")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = MaterialTheme.colorScheme.onSurface
            )
          }
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (urduProminent) toy.titleUr else toy.titleEn,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
          )
        }
      }
    },
    bottomBar = {
      // Bottom Action Bar
      Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Add to Cart Button
          OutlinedButton(
            onClick = { onAddToCart(toy, quantity) },
            enabled = !isOutOfStock,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("detail_add_to_cart_btn")
          ) {
            Icon(
              imageVector = Icons.Default.AddShoppingCart,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (urduProminent) "ٹوکری میں ڈالیں" else "Add to Cart",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }

          // Buy Now / COD Button
          Button(
            onClick = { onBuyNow(toy, quantity) },
            enabled = !isOutOfStock,
            colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("detail_buy_now_btn")
          ) {
            Icon(
              imageVector = Icons.Default.FlashOn,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (urduProminent) "ابھی خریدیں (COD)" else "Buy Now",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }
      }
    }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
    ) {
      // 1. Large Visual Showcase
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(240.dp)
          .background(category.themeColor.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = category.iconEmoji,
          fontSize = 90.sp
        )

        // Top Badges
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = category.themeColor
          ) {
            Text(
              text = "${category.nameUr} • ${category.nameEn}",
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          if (toy.badgeText.isNotBlank()) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = ToyRedPrimary
            ) {
              Text(
                text = toy.badgeText,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        // Bottom specs
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
            .padding(16.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
          ) {
            Text(
              text = "👶 عمر: ${toy.ageRange}",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (toy.stock > 0) ToyGreenSuccess.copy(alpha = 0.15f) else Color.Red.copy(alpha = 0.15f)
          ) {
            Text(
              text = if (toy.stock > 0) "اسٹاک: ${toy.stock} دستیاب" else "اسٹاک ختم (Out of stock)",
              color = if (toy.stock > 0) ToyGreenSuccess else Color.Red,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }

      // 2. Info & Pricing Card
      Column(
        modifier = Modifier.padding(16.dp)
      ) {
        // Rating & Review Count
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = ToyYellowTertiary.copy(alpha = 0.2f)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = ToyYellowTertiary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${toy.rating}",
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                color = Color.Black
              )
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "(${toy.reviewCount} مطمئن گاہکوں کے ریویوز)",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Titles
        Text(
          text = toy.titleUr,
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = toy.titleEn,
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Price Section
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.Bottom) {
            Text(
              text = "Rs. ${toy.pricePkr}",
              fontSize = 26.sp,
              fontWeight = FontWeight.Black,
              color = ToyRedPrimary
            )
            if (toy.originalPricePkr != null && toy.originalPricePkr > toy.pricePkr) {
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Rs. ${toy.originalPricePkr}",
                fontSize = 15.sp,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 2.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              val discount = ((toy.originalPricePkr - toy.pricePkr) * 100) / toy.originalPricePkr
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = ToyRedPrimary.copy(alpha = 0.15f)
              ) {
                Text(
                  text = "$discount% بچت",
                  color = ToyRedPrimary,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }

          // Quantity Stepper
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
              .padding(4.dp)
          ) {
            IconButton(
              onClick = { if (quantity > 1) quantity-- },
              enabled = quantity > 1,
              modifier = Modifier.size(32.dp)
            ) {
              Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
            }
            Text(
              text = quantity.toString(),
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              modifier = Modifier.padding(horizontal = 10.dp)
            )
            IconButton(
              onClick = { if (quantity < toy.stock) quantity++ },
              enabled = quantity < toy.stock,
              modifier = Modifier.size(32.dp)
            ) {
              Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        Spacer(modifier = Modifier.height(16.dp))

        // Pakistan Delivery & COD Highlight Card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = ToyGreenSuccess,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "کیش آن ڈیلیوری (Cash on Delivery) دستیاب ہے",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "پاکستان کے تمام شہروں میں 2 سے 4 دن میں ہوم ڈیلیوری۔ پارسل ملنے پر رقم ادا کریں۔",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Features List
        Text(
          text = if (urduProminent) "خصوصیات (Key Features)" else "Key Features",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        val features = toy.featuresList.split(",").map { it.trim() }
        features.forEach { feature ->
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 3.dp)
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = ToyBlueSecondary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = feature,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
          text = if (urduProminent) "کھلونے کی تفصیل (Description)" else "Description",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = toy.descriptionUr,
          fontSize = 14.sp,
          lineHeight = 22.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = toy.descriptionEn,
          fontSize = 13.sp,
          lineHeight = 20.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
