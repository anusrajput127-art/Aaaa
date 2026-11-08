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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderRecord
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyRedPrimary

@Composable
fun OrderConfirmationScreen(
  order: OrderRecord,
  urduProminent: Boolean,
  onViewOrders: () -> Unit,
  onContinueShopping: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onContinueShopping() }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Spacer(modifier = Modifier.height(12.dp))

    // Celebratory Icon
    Box(
      modifier = Modifier
        .size(90.dp)
        .clip(CircleShape)
        .background(ToyGreenSuccess.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .size(64.dp)
          .clip(CircleShape)
          .background(ToyGreenSuccess),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Success",
          tint = Color.White,
          modifier = Modifier.size(36.dp)
        )
      }
    }

    Text(
      text = "🎉 مبارک ہو! آپ کا آرڈر موصول ہو چکا ہے",
      fontSize = 20.sp,
      fontWeight = FontWeight.Black,
      color = MaterialTheme.colorScheme.onSurface,
      textAlign = TextAlign.Center
    )
    Text(
      text = "Thank you! Your order has been placed successfully at Toys Mart.",
      fontSize = 13.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      textAlign = TextAlign.Center
    )

    // Order Tracking ID Card
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = MaterialTheme.colorScheme.surfaceVariant,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "آرڈر نمبر (Order ID)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = order.orderId, fontSize = 20.sp, fontWeight = FontWeight.Black, color = ToyBlueSecondary)
        }
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = ToyGreenSuccess
        ) {
          Text(
            text = "تصدیق شدہ (Confirmed)",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }

    // Delivery Estimate Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.LocalShipping, contentDescription = null, tint = ToyBlueSecondary)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "ترسیل کی تفصیل (Delivery Timeline)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "🚚 آپ کا پارسل 2 سے 4 کام کے دنوں میں ڈیلیور کر دیا جائے گا۔",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Courier rider will contact your mobile number before delivery.",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Details Breakdown Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(
          text = "آرڈر کا خلاصہ (Order Summary)",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "گاہک کا نام:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = order.customerName, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "موبائل نمبر:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = order.customerPhone, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "شہر و پتہ:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = "${order.city}, ${order.address}", fontSize = 12.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.End, modifier = Modifier.weight(1f).padding(start = 12.dp))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "طریقہ ادائیگی:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = order.paymentMethod, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ToyGreenSuccess)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "کھلونے:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = order.itemsSummary, fontSize = 12.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.End, modifier = Modifier.weight(1f).padding(start = 12.dp))
        }

        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "قابل ادائیگی نقد رقم:", fontSize = 14.sp, fontWeight = FontWeight.Black)
          Text(
            text = "Rs. ${order.totalAmountPkr}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = ToyRedPrimary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Actions
    Button(
      onClick = onContinueShopping,
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary),
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("continue_shopping_btn")
    ) {
      Icon(Icons.Default.ShoppingBag, contentDescription = null)
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = if (urduProminent) "مزید خریداری جاری رکھیں (Continue Shopping)" else "Continue Shopping",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
      )
    }

    OutlinedButton(
      onClick = onViewOrders,
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("view_orders_btn")
    ) {
      Icon(Icons.Default.ListAlt, contentDescription = null, tint = ToyBlueSecondary)
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = if (urduProminent) "میرے تمام آرڈرز دیکھیں (View My Orders)" else "View My Orders",
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = ToyBlueSecondary
      )
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
