package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ToyCategories
import com.example.data.model.ToyItem
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToyYellowTertiary

@Composable
fun ToyCard(
  toy: ToyItem,
  urduProminent: Boolean,
  onClick: () -> Unit,
  onAddToCart: () -> Unit,
  modifier: Modifier = Modifier
) {
  val category = ToyCategories.list.find { it.id == toy.categoryId } ?: ToyCategories.ALL

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .testTag("toy_card_${toy.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column {
      // Image / Graphic Area with Badges
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(130.dp)
          .background(category.themeColor.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        // Large Playful Emoji Representation
        Text(
          text = category.iconEmoji,
          fontSize = 54.sp
        )

        // Top Badges (Discount & Stock)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .padding(8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Category Pill
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = category.themeColor
          ) {
            Text(
              text = if (urduProminent) category.nameUr else category.nameEn,
              color = Color.White,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
            )
          }

          // Stock or Discount
          if (toy.originalPricePkr != null && toy.originalPricePkr > toy.pricePkr) {
            val discountPercent = ((toy.originalPricePkr - toy.pricePkr) * 100) / toy.originalPricePkr
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = ToyRedPrimary
            ) {
              Text(
                text = "$discountPercent% OFF",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
              )
            }
          }
        }

        // Age Tag at bottom left
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(8.dp)
        ) {
          Text(
            text = toy.ageRange,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      // Details Area
      Column(
        modifier = Modifier.padding(12.dp)
      ) {
        // Rating & Review count
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(bottom = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Rating",
            tint = ToyYellowTertiary,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "${toy.rating} (${toy.reviewCount})",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.weight(1f))
          if (toy.stock <= 3 && toy.stock > 0) {
            Text(
              text = "صرف ${toy.stock} باقی (Low stock)",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = ToyRedPrimary
            )
          }
        }

        // Urdu Title (Prominent if enabled)
        if (urduProminent) {
          Text(
            text = toy.titleUr,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = toy.titleEn,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        } else {
          Text(
            text = toy.titleEn,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = toy.titleUr,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Price in PKR & Add to Cart
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "Rs. ${toy.pricePkr}",
              fontSize = 15.sp,
              fontWeight = FontWeight.Black,
              color = ToyRedPrimary
            )
            if (toy.originalPricePkr != null && toy.originalPricePkr > toy.pricePkr) {
              Text(
                text = "Rs. ${toy.originalPricePkr}",
                fontSize = 11.sp,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Button(
            onClick = onAddToCart,
            shape = RoundedCornerShape(10.dp),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.buttonColors(
              containerColor = if (toy.stock > 0) ToyBlueSecondary else Color.Gray
            ),
            enabled = toy.stock > 0,
            modifier = Modifier
              .height(36.dp)
              .testTag("add_to_cart_btn_${toy.id}")
          ) {
            Icon(
              imageVector = Icons.Default.AddShoppingCart,
              contentDescription = "Add to Cart",
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (toy.stock > 0) "خریدیں" else "ختم",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
