package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItem
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyRedPrimary

val PAKISTAN_CITIES = listOf(
  "Lahore (لاہور)",
  "Karachi (کراچی)",
  "Islamabad (اسلام آباد)",
  "Rawalpindi (راولپنڈی)",
  "Faisalabad (فیصل آباد)",
  "Multan (ملتان)",
  "Peshawar (پشاور)",
  "Quetta (کوئٹہ)",
  "Gujranwala (گوجرانوالہ)",
  "Sialkot (سیالکوٹ)",
  "Hyderabad (حیدرآباد)",
  "Bahawalpur (بہاولپور)",
  "Sargodha (سرگودھا)",
  "Abbottabad (ایبٹ آباد)",
  "Sukkur (سکھر)",
  "Other City (دیگر شہر)"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
  cartItems: List<CartItem>,
  subtotal: Int,
  deliveryFee: Int,
  discount: Int,
  urduProminent: Boolean,
  onBack: () -> Unit,
  onPlaceOrder: (String, String, String, String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  var customerName by remember { mutableStateOf("") }
  var customerPhone by remember { mutableStateOf("") }
  var selectedCity by remember { mutableStateOf(PAKISTAN_CITIES[0]) }
  var cityDropdownExpanded by remember { mutableStateOf(false) }
  var address by remember { mutableStateOf("") }
  var notes by remember { mutableStateOf("") }

  var nameError by remember { mutableStateOf(false) }
  var phoneError by remember { mutableStateOf(false) }
  var addressError by remember { mutableStateOf(false) }

  val grandTotal = (subtotal + deliveryFee - discount).coerceAtLeast(0)

  Scaffold(
    topBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = onBack, modifier = Modifier.testTag("checkout_back_btn")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (urduProminent) "آرڈر چیک آؤٹ (Checkout)" else "Checkout Details",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 1. Customer Information Card
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text(
            text = "1. گاہک کی معلومات (Customer Information)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )

          // Name
          OutlinedTextField(
            value = customerName,
            onValueChange = {
              customerName = it
              if (it.isNotBlank()) nameError = false
            },
            label = { Text("گاہک کا مکمل نام (Full Name)*") },
            placeholder = { Text("e.g. Muhammad Ali Khan") },
            isError = nameError,
            supportingText = { if (nameError) Text("براہ کرم اپنا نام درج کریں (Required)", color = Color.Red) },
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = ToyBlueSecondary) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("customer_name_input")
          )

          // Phone Number
          OutlinedTextField(
            value = customerPhone,
            onValueChange = {
              customerPhone = it
              if (it.length >= 10) phoneError = false
            },
            label = { Text("موبائل نمبر (WhatsApp / Phone)*") },
            placeholder = { Text("0300-1234567") },
            isError = phoneError,
            supportingText = { if (phoneError) Text("درست موبائل نمبر درج کریں (e.g. 03001234567)", color = Color.Red) },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = ToyBlueSecondary) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("customer_phone_input")
          )

          // City Selection
          ExposedDropdownMenuBox(
            expanded = cityDropdownExpanded,
            onExpandedChange = { cityDropdownExpanded = !cityDropdownExpanded }
          ) {
            OutlinedTextField(
              value = selectedCity,
              onValueChange = {},
              readOnly = true,
              label = { Text("شہر (City in Pakistan)*") },
              leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null, tint = ToyBlueSecondary) },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityDropdownExpanded) },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .testTag("city_dropdown")
            )
            ExposedDropdownMenu(
              expanded = cityDropdownExpanded,
              onDismissRequest = { cityDropdownExpanded = false }
            ) {
              PAKISTAN_CITIES.forEach { city ->
                DropdownMenuItem(
                  text = { Text(city, fontSize = 14.sp) },
                  onClick = {
                    selectedCity = city
                    cityDropdownExpanded = false
                  }
                )
              }
            }
          }

          // Address
          OutlinedTextField(
            value = address,
            onValueChange = {
              address = it
              if (it.isNotBlank()) addressError = false
            },
            label = { Text("مکمل گھر / گلی کا پتہ (Delivery Address)*") },
            placeholder = { Text("House #, Street, Area, Sector, Landmark...") },
            isError = addressError,
            supportingText = { if (addressError) Text("براہ کرم مکمل پتہ درج کریں", color = Color.Red) },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = ToyBlueSecondary) },
            minLines = 2,
            maxLines = 3,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("address_input")
          )

          // Notes
          OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("خصوصی ہدایات (Delivery Notes - اختیاری)") },
            placeholder = { Text("e.g. Call before coming, gift packing...") },
            leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          )
        }
      }

      // 2. Payment Method Card (Cash on Delivery Prominent)
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "2. طریقہ ادائیگی (Payment Method)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(10.dp))

          // COD Selected Box
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = ToyGreenSuccess.copy(alpha = 0.1f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, ToyGreenSuccess),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.RadioButtonChecked,
                contentDescription = null,
                tint = ToyGreenSuccess,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "کیش آن ڈیلیوری (Cash on Delivery - COD)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ToyGreenSuccess
                  ) {
                    Text(
                      text = "مفت و محفوظ",
                      color = Color.White,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                  text = "پارسل وصول کرنے پر نقد رقم ادا کریں۔ کسی پیشگی ادائیگی کی ضرورت نہیں ہے۔",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Easypaisa / JazzCash / Bank Transfer جلد شامل کیے جائیں گے",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      // 3. Final Order Summary & Confirmation
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "3. آرڈر کی تفصیلات (Order Total)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(10.dp))

          cartItems.forEach { item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "${item.quantity}x ${item.titleUr}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
              )
              Text(
                text = "Rs. ${item.pricePkr * item.quantity}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
          Spacer(modifier = Modifier.height(8.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "ذیلی رقم (Subtotal):", fontSize = 12.sp)
            Text(text = "Rs. $subtotal", fontSize = 12.sp)
          }
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "ڈیلیوری چارجز:", fontSize = 12.sp)
            Text(text = if (deliveryFee == 0) "مفت" else "Rs. $deliveryFee", fontSize = 12.sp)
          }
          if (discount > 0) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text(text = "ڈسکاؤنٹ رعایت:", fontSize = 12.sp, color = ToyGreenSuccess)
              Text(text = "- Rs. $discount", fontSize = 12.sp, color = ToyGreenSuccess)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "کل قابل ادائیگی رقم:",
              fontSize = 15.sp,
              fontWeight = FontWeight.Black
            )
            Text(
              text = "Rs. $grandTotal",
              fontSize = 22.sp,
              fontWeight = FontWeight.Black,
              color = ToyRedPrimary
            )
          }
        }
      }

      // 4. Submit Order Button
      Button(
        onClick = {
          var valid = true
          if (customerName.isBlank()) {
            nameError = true
            valid = false
          }
          if (customerPhone.trim().length < 10) {
            phoneError = true
            valid = false
          }
          if (address.isBlank()) {
            addressError = true
            valid = false
          }
          if (valid) {
            onPlaceOrder(customerName.trim(), customerPhone.trim(), selectedCity, address.trim(), notes.trim())
          }
        },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ToyGreenSuccess),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .padding(bottom = 8.dp)
          .testTag("confirm_order_btn")
      ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (urduProminent) "آرڈر مکمل کریں • Rs. $grandTotal (COD)" else "Place Order • Rs. $grandTotal (COD)",
          fontSize = 15.sp,
          fontWeight = FontWeight.Black,
          color = Color.White
        )
      }
    }
  }
}
