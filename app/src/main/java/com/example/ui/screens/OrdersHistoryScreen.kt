package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.OrderRecord
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyPurpleAccent
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToyYellowTertiary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersHistoryScreen(
  orders: List<OrderRecord>,
  urduProminent: Boolean,
  onStartShopping: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedFilter by remember { mutableStateOf("All") }
  val filterOptions = listOf("All", "Pending", "Confirmed", "Shipped", "Delivered")

  val filteredOrders = if (selectedFilter == "All") {
    orders
  } else {
    orders.filter { it.status.equals(selectedFilter, ignoreCase = true) }
  }

  val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Column {
        Text(
          text = if (urduProminent) "میرے آرڈرز (Order History)" else "Order History",
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Track your parcel delivery and Cash on Delivery order history",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Filter Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
      ) {
        items(filterOptions) { filter ->
          val isSelected = selectedFilter == filter
          val count = if (filter == "All") orders.size else orders.count { it.status.equals(filter, ignoreCase = true) }
          val filterUrdu = when (filter) {
            "All" -> "تمام"
            "Pending" -> "زیر التواء"
            "Confirmed" -> "تصدیق شدہ"
            "Shipped" -> "روانہ"
            "Delivered" -> "پہنچ گیا"
            else -> filter
          }

          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) ToyBlueSecondary else MaterialTheme.colorScheme.surface,
            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .clickable { selectedFilter = filter }
          ) {
            Text(
              text = "$filterUrdu ($count)",
              color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 12.sp,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }
      }
    }

    if (filteredOrders.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
        ) {
          Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.ReceiptLong,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "ابھی کوئی آرڈر نہیں ملا",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "No orders found in this category. Place an order to see it here!",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onStartShopping,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary)
            ) {
              Text("ابھی آرڈر کریں (Shop Now)")
            }
          }
        }
      }
    } else {
      items(filteredOrders, key = { it.orderId }) { order ->
        val (statusColor, statusUrdu) = when (order.status) {
          "Pending" -> Pair(ToyYellowTertiary, "زیر التواء (Pending)")
          "Confirmed" -> Pair(ToyBlueSecondary, "تصدیق شدہ (Confirmed)")
          "Shipped" -> Pair(ToyPurpleAccent, "روانہ ہو چکا (Shipped)")
          "Delivered" -> Pair(ToyGreenSuccess, "پہنچ گیا (Delivered)")
          else -> Pair(Color.Gray, order.status)
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("order_card_${order.orderId}")
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Top Bar: Order ID & Status
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = order.orderId,
                  fontWeight = FontWeight.Black,
                  fontSize = 16.sp,
                  color = ToyBlueSecondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "• ${order.totalItemsCount} کھلونے",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = statusColor.copy(alpha = 0.15f)
              ) {
                Text(
                  text = statusUrdu,
                  color = statusColor,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "تاریخ: ${dateFormat.format(Date(order.timestamp))}",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(modifier = Modifier.height(8.dp))

            // Items Summary
            Text(
              text = order.itemsSummary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Destination
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${order.city} • ${order.address}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Payment and Total
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = ToyGreenSuccess.copy(alpha = 0.12f)
              ) {
                Text(
                  text = "کیش آن ڈیلیوری (COD)",
                  color = ToyGreenSuccess,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }

              Text(
                text = "Rs. ${order.totalAmountPkr}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = ToyRedPrimary
              )
            }
          }
        }
      }
    }
  }
}
