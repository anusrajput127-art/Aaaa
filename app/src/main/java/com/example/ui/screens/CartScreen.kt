package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItem
import com.example.data.model.ToyCategories
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToyYellowTertiary

@Composable
fun CartScreen(
  cartItems: List<CartItem>,
  discountAmount: Int,
  promoCode: String,
  urduProminent: Boolean,
  onUpdateQuantity: (CartItem, Int) -> Unit,
  onRemoveItem: (CartItem) -> Unit,
  onClearCart: () -> Unit,
  onApplyPromo: (String, Int) -> Unit,
  onProceedToCheckout: () -> Unit,
  onStartShopping: () -> Unit,
  modifier: Modifier = Modifier
) {
  var promoInput by remember { mutableStateOf(promoCode) }

  val subtotal = cartItems.sumOf { it.pricePkr * it.quantity }
  val freeDeliveryThreshold = 2500
  val deliveryFee = if (subtotal >= freeDeliveryThreshold || subtotal == 0) 0 else 200
  val grandTotal = (subtotal + deliveryFee - discountAmount).coerceAtLeast(0)

  if (cartItems.isEmpty()) {
    // Empty Cart State
    Box(
      modifier = modifier
        .fillMaxSize()
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(32.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(90.dp)
              .clip(CircleShape)
              .background(ToyRedPrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = "🛒", fontSize = 42.sp)
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "آپ کی ٹوکری خالی ہے",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Your shopping cart is currently empty. Explore our wonderful collection of toys!",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = onStartShopping,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("start_shopping_btn")
          ) {
            Icon(Icons.Default.ShoppingBag, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (urduProminent) "کھلونے دیکھیں (Start Shopping)" else "Start Shopping",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    }
  } else {
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header with clear cart option
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = if (urduProminent) "شاپنگ کارٹ (Shopping Cart)" else "Shopping Cart",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "${cartItems.sumOf { it.quantity }} items selected",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          OutlinedButton(
            onClick = onClearCart,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("clear_cart_btn")
          ) {
            Icon(Icons.Default.DeleteOutline, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "خالی کریں", fontSize = 11.sp, color = Color.Red)
          }
        }
      }

      // Free Shipping Progress
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            val remaining = (freeDeliveryThreshold - subtotal).coerceAtLeast(0)
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = if (remaining == 0) ToyGreenSuccess else ToyBlueSecondary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (remaining == 0) "🎉 مبارک ہو! آپ کے لیے مفت ڈیلیوری فعال ہے"
                else "صرف Rs. $remaining کا مزید کھلونا شامل کریں اور مفت ڈیلیوری حاصل کریں!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            val progress = (subtotal.toFloat() / freeDeliveryThreshold).coerceIn(0f, 1f)
            LinearProgressIndicator(
              progress = { progress },
              color = if (remaining == 0) ToyGreenSuccess else ToyBlueSecondary,
              trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
            )
          }
        }
      }

      // Cart Items
      items(cartItems, key = { it.cartId }) { item ->
        val category = ToyCategories.list.find { it.id == item.categoryId } ?: ToyCategories.ALL

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("cart_item_${item.cartId}")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Category Icon Box
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(category.themeColor.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Text(text = category.iconEmoji, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Title & Unit Price
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (urduProminent) item.titleUr else item.titleEn,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
              )
              Text(
                text = if (urduProminent) item.titleEn else item.titleUr,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Rs. ${item.pricePkr} ہر ایک",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = ToyRedPrimary
              )
            }

            // Quantity Stepper & Delete
            Column(horizontalAlignment = Alignment.End) {
              IconButton(
                onClick = { onRemoveItem(item) },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Delete,
                  contentDescription = "Remove",
                  tint = Color.Gray,
                  modifier = Modifier.size(16.dp)
                )
              }

              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                  .padding(2.dp)
              ) {
                IconButton(
                  onClick = { onUpdateQuantity(item, item.quantity - 1) },
                  modifier = Modifier.size(26.dp)
                ) {
                  Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(14.dp))
                }
                Text(
                  text = item.quantity.toString(),
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp)
                )
                IconButton(
                  onClick = { onUpdateQuantity(item, item.quantity + 1) },
                  enabled = item.quantity < item.stock,
                  modifier = Modifier.size(26.dp)
                ) {
                  Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(14.dp))
                }
              }
            }
          }
        }
      }

      // Promo Code Box
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "کوپن کوڈ (Promo Code)",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              OutlinedTextField(
                value = promoInput,
                onValueChange = { promoInput = it },
                placeholder = { Text("Enter promo code (e.g. TOYS10)", fontSize = 12.sp) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("promo_code_input")
              )
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = { onApplyPromo(promoInput, subtotal) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ToyBlueSecondary),
                modifier = Modifier.testTag("apply_promo_btn")
              ) {
                Text("لاگو کریں", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Hint: 10% رعایت کے لیے کوڈ TOYS10 یا BACHPAN استعمال کریں",
              fontSize = 10.sp,
              color = ToyBlueSecondary
            )
          }
        }
      }

      // Bill Summary Card
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "آرڈر کا خلاصہ (Order Summary)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "ذیلی رقم (Subtotal):", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(text = "Rs. $subtotal", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "ڈیلیوری چارجز (Delivery):", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(
                text = if (deliveryFee == 0) "مفت (FREE)" else "Rs. $deliveryFee",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (deliveryFee == 0) ToyGreenSuccess else MaterialTheme.colorScheme.onSurface
              )
            }

            if (discountAmount > 0) {
              Spacer(modifier = Modifier.height(6.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "ڈسکاؤنٹ رعایت (Discount):", fontSize = 13.sp, color = ToyGreenSuccess)
                Text(text = "- Rs. $discountAmount", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ToyGreenSuccess)
              }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "کل رقم (Total Amount):",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Rs. $grandTotal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = ToyRedPrimary
              )
            }
          }
        }
      }

      // Checkout Button
      item {
        Button(
          onClick = onProceedToCheckout,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(bottom = 90.dp)
            .testTag("checkout_btn")
        ) {
          Text(
            text = if (urduProminent) "چیک آؤٹ کریں • Rs. $grandTotal (کیش آن ڈیلیوری)" else "Proceed to Checkout • Rs. $grandTotal",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
          )
        }
      }
    }
  }
}
