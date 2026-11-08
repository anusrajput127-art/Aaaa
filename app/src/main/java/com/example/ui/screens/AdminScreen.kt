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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.OrderRecord
import com.example.data.model.ToyCategories
import com.example.data.model.ToyItem
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyPurpleAccent
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToyYellowTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
  toys: List<ToyItem>,
  orders: List<OrderRecord>,
  totalRevenue: Int,
  urduProminent: Boolean,
  onSaveToy: (Long, String, String, String, Int, Int?, Int, String, String, String, String, Boolean) -> Unit,
  onDeleteToy: (ToyItem) -> Unit,
  onUpdateStock: (Long, Int) -> Unit,
  onUpdateOrderStatus: (String, String) -> Unit,
  onResetSampleData: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabTitles = listOf(
    "کھلونے (Toys)",
    "آرڈرز (Orders)",
    "اسٹور گائیڈ (Guide)"
  )

  var showToyDialog by remember { mutableStateOf(false) }
  var toyToEdit by remember { mutableStateOf<ToyItem?>(null) }
  var toyToDelete by remember { mutableStateOf<ToyItem?>(null) }
  var showResetConfirm by remember { mutableStateOf(false) }

  Scaffold(
    floatingActionButton = {
      if (selectedTab == 0) {
        FloatingActionButton(
          onClick = {
            toyToEdit = null
            showToyDialog = true
          },
          containerColor = ToyRedPrimary,
          contentColor = Color.White,
          modifier = Modifier
            .padding(bottom = 70.dp)
            .testTag("admin_add_toy_fab")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Add, contentDescription = "Add Toy")
            Spacer(modifier = Modifier.width(4.dp))
            Text("نیا کھلونا شامل کریں", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      // 1. Admin Header & Store KPIs
      Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Toys Mart انتظامی پینل",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Store Owner Admin Control Dashboard",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            OutlinedButton(
              onClick = { showResetConfirm = true },
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("ری سیٹ", fontSize = 11.sp)
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // 4 KPI Cards
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // Revenue
            Card(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = ToyGreenSuccess.copy(alpha = 0.12f))
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "کل فروخت (Sales)", fontSize = 10.sp, color = ToyGreenSuccess, fontWeight = FontWeight.Bold)
                Text(text = "Rs. $totalRevenue", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
              }
            }

            // Total Orders
            Card(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = ToyBlueSecondary.copy(alpha = 0.12f))
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "کل آرڈرز", fontSize = 10.sp, color = ToyBlueSecondary, fontWeight = FontWeight.Bold)
                Text(text = "${orders.size}", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
              }
            }

            // Products
            Card(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = ToyRedPrimary.copy(alpha = 0.12f))
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "کل کھلونے", fontSize = 10.sp, color = ToyRedPrimary, fontWeight = FontWeight.Bold)
                Text(text = "${toys.size}", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
              }
            }
          }
        }
      }

      // 2. Tab Navigation
      PrimaryTabRow(
        selectedTabIndex = selectedTab,
        containerColor = MaterialTheme.colorScheme.surface
      ) {
        tabTitles.forEachIndexed { index, title ->
          Tab(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            text = {
              Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
              )
            }
          )
        }
      }

      // 3. Tab Content
      when (selectedTab) {
        // TAB 0: Manage Toys
        0 -> {
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            item {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "دستیاب کھلونوں کی فہرست (${toys.size})",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "قیمت اور اسٹاک تبدیل کریں",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            items(toys, key = { it.id }) { toy ->
              val category = ToyCategories.list.find { it.id == toy.categoryId } ?: ToyCategories.ALL
              Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
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
                      .size(52.dp)
                      .clip(RoundedCornerShape(10.dp))
                      .background(category.themeColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(text = category.iconEmoji, fontSize = 24.sp)
                  }

                  Spacer(modifier = Modifier.width(10.dp))

                  // Details
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = toy.titleUr,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                      maxLines = 1
                    )
                    Text(
                      text = toy.titleEn,
                      fontSize = 11.sp,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(
                        text = "Rs. ${toy.pricePkr}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = ToyRedPrimary
                      )
                      Spacer(modifier = Modifier.width(8.dp))
                      Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (toy.stock > 0) ToyGreenSuccess.copy(alpha = 0.15f) else Color.Red.copy(alpha = 0.15f)
                      ) {
                        Text(
                          text = "اسٹاک: ${toy.stock}",
                          fontSize = 10.sp,
                          fontWeight = FontWeight.Bold,
                          color = if (toy.stock > 0) ToyGreenSuccess else Color.Red,
                          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                      }
                    }
                  }

                  // Action buttons: Edit, Delete, Quick stock
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                      onClick = {
                        toyToEdit = toy
                        showToyDialog = true
                      },
                      modifier = Modifier.size(34.dp)
                    ) {
                      Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ToyBlueSecondary, modifier = Modifier.size(18.dp))
                    }
                    IconButton(
                      onClick = { toyToDelete = toy },
                      modifier = Modifier.size(34.dp)
                    ) {
                      Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(18.dp))
                    }
                  }
                }
              }
            }
          }
        }

        // TAB 1: Manage Orders
        1 -> {
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            item {
              Text(
                text = "تمام موصولہ آرڈرز (${orders.size})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }

            items(orders, key = { it.orderId }) { order ->
              var statusExpanded by remember { mutableStateOf(false) }
              val orderStatusOptions = listOf("Pending", "Confirmed", "Shipped", "Delivered", "Cancelled")

              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Column {
                      Text(text = order.orderId, fontSize = 15.sp, fontWeight = FontWeight.Black, color = ToyBlueSecondary)
                      Text(text = "گاہک: ${order.customerName}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    // Status Dropdown
                    Box {
                      Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when (order.status) {
                          "Pending" -> ToyYellowTertiary.copy(alpha = 0.2f)
                          "Confirmed" -> ToyBlueSecondary.copy(alpha = 0.2f)
                          "Shipped" -> ToyPurpleAccent.copy(alpha = 0.2f)
                          "Delivered" -> ToyGreenSuccess.copy(alpha = 0.2f)
                          else -> Color.Gray.copy(alpha = 0.2f)
                        },
                        modifier = Modifier
                          .clip(RoundedCornerShape(10.dp))
                          .clickable { statusExpanded = true }
                      ) {
                        Text(
                          text = "${order.status} ▾",
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold,
                          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                      }

                      DropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                      ) {
                        orderStatusOptions.forEach { statusOption ->
                          DropdownMenuItem(
                            text = { Text(statusOption) },
                            onClick = {
                              onUpdateOrderStatus(order.orderId, statusOption)
                              statusExpanded = false
                            }
                          )
                        }
                      }
                    }
                  }

                  Spacer(modifier = Modifier.height(6.dp))
                  Text(text = "📞 فون: ${order.customerPhone}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                  Text(text = "📍 پتہ: ${order.city}, ${order.address}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                  if (order.orderNotes.isNotBlank()) {
                    Text(text = "📝 ہدایت: ${order.orderNotes}", fontSize = 11.sp, color = ToyBlueSecondary)
                  }

                  Spacer(modifier = Modifier.height(8.dp))
                  Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                  Spacer(modifier = Modifier.height(8.dp))

                  Text(text = "سامان: ${order.itemsSummary}", fontSize = 12.sp, fontWeight = FontWeight.Medium)

                  Spacer(modifier = Modifier.height(6.dp))
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(text = "طریقہ: کیش آن ڈیلیوری (COD)", fontSize = 11.sp, color = ToyGreenSuccess, fontWeight = FontWeight.Bold)
                    Text(text = "Rs. ${order.totalAmountPkr}", fontSize = 16.sp, fontWeight = FontWeight.Black, color = ToyRedPrimary)
                  }
                }
              }
            }
          }
        }

        // TAB 2: Cloud Setup & Production Guide (Answers user prompt requirements directly)
        2 -> {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            // Notice Card
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.Info, contentDescription = null, tint = ToyBlueSecondary)
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "ڈیٹا بیس اور پروڈکشن کی تفصیل (Setup Guide)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "جیسا کہ آپ کے پرامپٹ میں پوچھا گیا تھا، یہاں واضح رہنمائی موجود ہے کہ یہ ایپلیکیشن کس طرح کام کر رہی ہے اور جب آپ اسے عام گاہکوں کے لیے لائیو کرنا چاہیں تو کیا اقدامات درکار ہوں گے:",
                  fontSize = 12.sp,
                  lineHeight = 18.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
            }

            // Step 1: Database Setup
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = "1. ڈیٹا بیس سیٹ اپ (Database Setup)",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = ToyRedPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "• موجودہ حالت: ایپلیکیشن مکمل طور پر تیز رفتار Android Room (SQLite) لوکل ڈیٹا بیس پر چل رہی ہے۔ تمام کھلونے، شاپنگ کارٹ اور آرڈرز محفوظ رہتے ہیں۔\n• کلاؤڈ سنک (Cloud Sync): اگر آپ چاہتے ہیں کہ گاہک اپنے فون سے آرڈر دیں اور وہ فوراً آپ کے ایڈمن پینل میں ظاہر ہو، تو اس کے لیے Firebase Firestore یا REST API کی ضرورت ہوتی ہے تاکہ دونوں ڈیوائسز ایک ہی کلاؤڈ ڈیٹا بیس سے جڑ سکیں۔",
                  fontSize = 12.sp,
                  lineHeight = 18.sp
                )
              }
            }

            // Step 2: Authentication
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = "2. تصدیق و سیکیورٹی (Authentication)",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = ToyBlueSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "• گاہکوں کے لیے: آسان چیک آؤٹ کے لیے موبائل نمبر اور نام کافی ہے (جیسے عام طور پر پاکستان میں کیش آن ڈیلیوری میں ہوتا ہے)۔\n• ایڈمن کے لیے: صرف اسٹور کا مالک ہی کھلونے شامل یا حذف کر سکے، اس کے لیے گوگل سائن ان یا ایڈمن پاس ورڈ لاگ ان لگایا جا سکتا ہے۔",
                  fontSize = 12.sp,
                  lineHeight = 18.sp
                )
              }
            }

            // Step 3: Android APK Generation
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = "3. اینڈرائیڈ APK کیسے ڈاؤنلوڈ کریں؟",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = ToyGreenSuccess
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "• AI Studio میں سیٹنگز مینو سے آپ براہ راست 'Export Project' کر سکتے ہیں یا APK / AAB بلڈ جنریٹ کر کے اپنے اینڈرائیڈ موبائل پر انسٹال کر سکتے ہیں۔\n• اس APK کو آپ گوگل ڈرائیو پر اپ لوڈ کر کے اپنے کسٹمرز یا دوستوں کے ساتھ واٹس ایپ پر باآسانی شیئر کر سکتے ہیں۔",
                  fontSize = 12.sp,
                  lineHeight = 18.sp
                )
              }
            }

            // Step 4: Sample Data vs Real Orders
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = "4. نمونے کا ڈیٹا بمقابلہ اصلی آرڈرز",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = ToyYellowTertiary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "• ابتدائی طور پر دکھائے گئے آرڈرز (TM-94021 وغیرہ) نمونے کے ہیں تاکہ آپ ٹیسٹ کر سکیں۔\n• جب آپ یا گاہک چیک آؤٹ فارم سے نیا آرڈر پلیس کریں گے تو وہ حقیقی طور پر ڈیٹا بیس میں نیا آرڈر بنے گا اور اسٹاک خود بخود کم ہو جائے گا۔",
                  fontSize = 12.sp,
                  lineHeight = 18.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(70.dp))
          }
        }
      }
    }
  }

  // Add / Edit Toy Dialog
  if (showToyDialog) {
    AddEditToyDialog(
      toy = toyToEdit,
      onDismiss = { showToyDialog = false },
      onSave = { id, titleEn, titleUr, category, price, origPrice, stock, descEn, descUr, age, badge, featured ->
        onSaveToy(id, titleEn, titleUr, category, price, origPrice, stock, descEn, descUr, age, badge, featured)
        showToyDialog = false
      }
    )
  }

  // Delete Confirmation Dialog
  if (toyToDelete != null) {
    AlertDialog(
      onDismissRequest = { toyToDelete = null },
      title = { Text("کھلونا حذف کریں؟ (Delete Toy)") },
      text = { Text("کیا آپ واقعی \"${toyToDelete?.titleUr}\" کو اسٹور سے حذف کرنا چاہتے ہیں؟") },
      confirmButton = {
        Button(
          onClick = {
            toyToDelete?.let { onDeleteToy(it) }
            toyToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
          Text("حذف کریں (Delete)")
        }
      },
      dismissButton = {
        TextButton(onClick = { toyToDelete = null }) {
          Text("منسوخ")
        }
      }
    )
  }

  // Reset Confirmation Dialog
  if (showResetConfirm) {
    AlertDialog(
      onDismissRequest = { showResetConfirm = false },
      title = { Text("ڈیٹا دوبارہ سیٹ کریں؟") },
      text = { Text("اس سے تمام کھلونے اور نمونے کے آرڈرز اصل حالت میں آ جائیں گے۔") },
      confirmButton = {
        Button(
          onClick = {
            onResetSampleData()
            showResetConfirm = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary)
        ) {
          Text("ری سیٹ کریں")
        }
      },
      dismissButton = {
        TextButton(onClick = { showResetConfirm = false }) {
          Text("منسوخ")
        }
      }
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditToyDialog(
  toy: ToyItem?,
  onDismiss: () -> Unit,
  onSave: (Long, String, String, String, Int, Int?, Int, String, String, String, String, Boolean) -> Unit
) {
  var titleUr by remember { mutableStateOf(toy?.titleUr ?: "") }
  var titleEn by remember { mutableStateOf(toy?.titleEn ?: "") }
  var selectedCategory by remember { mutableStateOf(toy?.categoryId ?: "cars") }
  var categoryExpanded by remember { mutableStateOf(false) }
  var priceStr by remember { mutableStateOf(toy?.pricePkr?.toString() ?: "1500") }
  var originalPriceStr by remember { mutableStateOf(toy?.originalPricePkr?.toString() ?: "") }
  var stockStr by remember { mutableStateOf(toy?.stock?.toString() ?: "10") }
  var ageRange by remember { mutableStateOf(toy?.ageRange ?: "3+ Years") }
  var badgeText by remember { mutableStateOf(toy?.badgeText ?: "") }
  var descUr by remember { mutableStateOf(toy?.descriptionUr ?: "") }
  var descEn by remember { mutableStateOf(toy?.descriptionEn ?: "") }

  var titleError by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = if (toy == null) "نیا کھلونا شامل کریں (Add Toy)" else "کھلونا ایڈٹ کریں (Edit Toy)",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Urdu Title
        OutlinedTextField(
          value = titleUr,
          onValueChange = {
            titleUr = it
            if (it.isNotBlank()) titleError = false
          },
          label = { Text("کھلونے کا نام (Urdu)*") },
          placeholder = { Text("مثلاً ریموٹ کنٹرول گاڑی") },
          isError = titleError,
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        // English Title
        OutlinedTextField(
          value = titleEn,
          onValueChange = { titleEn = it },
          label = { Text("Toy Title (English)") },
          placeholder = { Text("e.g. Remote Control Sports Car") },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        // Category Selector
        ExposedDropdownMenuBox(
          expanded = categoryExpanded,
          onExpandedChange = { categoryExpanded = !categoryExpanded }
        ) {
          val catObj = ToyCategories.list.find { it.id == selectedCategory } ?: ToyCategories.CARS
          OutlinedTextField(
            value = "${catObj.iconEmoji} ${catObj.nameUr} (${catObj.nameEn})",
            onValueChange = {},
            readOnly = true,
            label = { Text("کھلونے کی قسم (Category)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.menuAnchor().fillMaxWidth()
          )
          ExposedDropdownMenu(
            expanded = categoryExpanded,
            onDismissRequest = { categoryExpanded = false }
          ) {
            ToyCategories.list.forEach { category ->
              DropdownMenuItem(
                text = { Text("${category.iconEmoji} ${category.nameUr} (${category.nameEn})") },
                onClick = {
                  selectedCategory = category.id
                  categoryExpanded = false
                }
              )
            }
          }
        }

        // Price in PKR & Stock
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = priceStr,
            onValueChange = { priceStr = it },
            label = { Text("قیمت (Rs.)*") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          )

          OutlinedTextField(
            value = stockStr,
            onValueChange = { stockStr = it },
            label = { Text("اسٹاک (Stock)*") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          )
        }

        // Original Price (Discount) & Age
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = originalPriceStr,
            onValueChange = { originalPriceStr = it },
            label = { Text("اصل قیمت (قبل از رعایت)") },
            placeholder = { Text("e.g. 2000") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          )

          OutlinedTextField(
            value = ageRange,
            onValueChange = { ageRange = it },
            label = { Text("عمر (Age Group)") },
            placeholder = { Text("3+ Years") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          )
        }

        // Badge Text
        OutlinedTextField(
          value = badgeText,
          onValueChange = { badgeText = it },
          label = { Text("بیج / پیشکش (Badge Text)") },
          placeholder = { Text("e.g. Best Seller, New, 20% OFF") },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        // Urdu Description
        OutlinedTextField(
          value = descUr,
          onValueChange = { descUr = it },
          label = { Text("تفصیل (Description in Urdu)") },
          placeholder = { Text("کھلونے کی خصوصیات اور بچوں کے لیے حفاظت...") },
          minLines = 2,
          maxLines = 3,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (titleUr.isBlank() && titleEn.isBlank()) {
            titleError = true
            return@Button
          }
          val price = priceStr.toIntOrNull() ?: 1000
          val originalPrice = originalPriceStr.toIntOrNull()
          val stock = stockStr.toIntOrNull() ?: 10
          val finalTitleEn = if (titleEn.isBlank()) titleUr else titleEn
          val finalTitleUr = if (titleUr.isBlank()) titleEn else titleUr
          val finalDescUr = if (descUr.isBlank()) "اعلی معیار کا کھلونا بچوں کی تفریح کے لیے۔" else descUr
          val finalDescEn = if (descEn.isBlank()) finalDescUr else descEn

          onSave(
            toy?.id ?: 0L,
            finalTitleEn,
            finalTitleUr,
            selectedCategory,
            price,
            originalPrice,
            stock,
            finalDescEn,
            finalDescUr,
            ageRange,
            badgeText,
            toy?.isFeatured ?: false
          )
        },
        colors = ButtonDefaults.buttonColors(containerColor = ToyRedPrimary)
      ) {
        Text("محفوظ کریں (Save)")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("منسوخ")
      }
    }
  )
}
