package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.Screen
import com.example.ui.ToysMartViewModel
import com.example.ui.components.ToysHeader
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CheckoutScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderConfirmationScreen
import com.example.ui.screens.OrdersHistoryScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToysMartTheme

class MainActivity : ComponentActivity() {
  private val viewModel: ToysMartViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ToysMartTheme {
        ToysMartApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun ToysMartApp(viewModel: ToysMartViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val urduProminent by viewModel.urduProminent.collectAsState()
  val cartItems by viewModel.cartItems.collectAsState()
  val cartCount by viewModel.cartCount.collectAsState()
  val orders by viewModel.orders.collectAsState()
  val totalRevenue by viewModel.totalRevenue.collectAsState()
  val filteredToys by viewModel.filteredToys.collectAsState()
  val featuredToys by viewModel.featuredToys.collectAsState()
  val allToys by viewModel.allToys.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val selectedSort by viewModel.selectedSort.collectAsState()
  val discountAmount by viewModel.discountAmount.collectAsState()
  val promoCode by viewModel.promoCode.collectAsState()
  val userMessage by viewModel.userMessage.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(userMessage) {
    userMessage?.let {
      snackbarHostState.showSnackbar(it)
      viewModel.clearUserMessage()
    }
  }

  // Handle hardware back button when on sub-screens
  if (currentScreen !is Screen.Home) {
    BackHandler {
      viewModel.navigateBack()
    }
  }

  val showBottomNav = currentScreen is Screen.Home ||
      currentScreen is Screen.Categories ||
      currentScreen is Screen.Cart ||
      currentScreen is Screen.OrdersHistory ||
      currentScreen is Screen.Admin

  val showTopHeader = currentScreen is Screen.Home ||
      currentScreen is Screen.Categories ||
      currentScreen is Screen.OrdersHistory

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      if (showTopHeader) {
        ToysHeader(
          cartCount = cartCount,
          urduProminent = urduProminent,
          onToggleLanguage = { viewModel.toggleUrduProminent() },
          onOpenCart = { viewModel.navigateTo(Screen.Cart) },
          onOpenAdmin = { viewModel.navigateTo(Screen.Admin) }
        )
      }
    },
    bottomBar = {
      if (showBottomNav) {
        NavigationBar(
          containerColor = MaterialTheme.colorScheme.surface,
          tonalElevation = 6.dp,
          modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("bottom_nav_bar")
        ) {
          // 1. Home
          NavigationBarItem(
            selected = currentScreen is Screen.Home,
            onClick = { viewModel.navigateTo(Screen.Home) },
            icon = {
              Icon(
                imageVector = if (currentScreen is Screen.Home) Icons.Filled.Home else Icons.Outlined.Home,
                contentDescription = "Home"
              )
            },
            label = {
              Text(
                text = if (urduProminent) "ہوم" else "Home",
                fontSize = 11.sp,
                fontWeight = if (currentScreen is Screen.Home) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = ToyRedPrimary,
              selectedTextColor = ToyRedPrimary,
              indicatorColor = ToyRedPrimary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_item_home")
          )

          // 2. Categories
          NavigationBarItem(
            selected = currentScreen is Screen.Categories,
            onClick = { viewModel.navigateTo(Screen.Categories) },
            icon = {
              Icon(
                imageVector = if (currentScreen is Screen.Categories) Icons.Filled.Category else Icons.Outlined.Category,
                contentDescription = "Categories"
              )
            },
            label = {
              Text(
                text = if (urduProminent) "اقسام" else "Categories",
                fontSize = 11.sp,
                fontWeight = if (currentScreen is Screen.Categories) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = ToyBlueSecondary,
              selectedTextColor = ToyBlueSecondary,
              indicatorColor = ToyBlueSecondary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_item_categories")
          )

          // 3. Cart with Badge
          NavigationBarItem(
            selected = currentScreen is Screen.Cart,
            onClick = { viewModel.navigateTo(Screen.Cart) },
            icon = {
              BadgedBox(
                badge = {
                  if (cartCount > 0) {
                    Badge(
                      containerColor = ToyRedPrimary,
                      contentColor = Color.White
                    ) {
                      Text(cartCount.toString(), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                  }
                }
              ) {
                Icon(
                  imageVector = if (currentScreen is Screen.Cart) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                  contentDescription = "Cart"
                )
              }
            },
            label = {
              Text(
                text = if (urduProminent) "ٹوکری" else "Cart",
                fontSize = 11.sp,
                fontWeight = if (currentScreen is Screen.Cart) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = ToyRedPrimary,
              selectedTextColor = ToyRedPrimary,
              indicatorColor = ToyRedPrimary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_item_cart")
          )

          // 4. Orders History
          NavigationBarItem(
            selected = currentScreen is Screen.OrdersHistory,
            onClick = { viewModel.navigateTo(Screen.OrdersHistory) },
            icon = {
              Icon(
                imageVector = if (currentScreen is Screen.OrdersHistory) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
                contentDescription = "Orders"
              )
            },
            label = {
              Text(
                text = if (urduProminent) "آرڈرز" else "Orders",
                fontSize = 11.sp,
                fontWeight = if (currentScreen is Screen.OrdersHistory) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = ToyBlueSecondary,
              selectedTextColor = ToyBlueSecondary,
              indicatorColor = ToyBlueSecondary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_item_orders")
          )

          // 5. Admin
          NavigationBarItem(
            selected = currentScreen is Screen.Admin,
            onClick = { viewModel.navigateTo(Screen.Admin) },
            icon = {
              Icon(
                imageVector = if (currentScreen is Screen.Admin) Icons.Filled.AdminPanelSettings else Icons.Outlined.AdminPanelSettings,
                contentDescription = "Admin"
              )
            },
            label = {
              Text(
                text = if (urduProminent) "ایڈمن" else "Admin",
                fontSize = 11.sp,
                fontWeight = if (currentScreen is Screen.Admin) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = ToyBlueSecondary,
              selectedTextColor = ToyBlueSecondary,
              indicatorColor = ToyBlueSecondary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_item_admin")
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "ScreenTransition"
      ) { screen ->
        when (screen) {
          is Screen.Home -> {
            HomeScreen(
              toys = filteredToys,
              featuredToys = featuredToys,
              searchQuery = searchQuery,
              selectedCategory = selectedCategory,
              selectedSort = selectedSort,
              urduProminent = urduProminent,
              onSearchQueryChanged = { viewModel.setSearchQuery(it) },
              onCategorySelected = { viewModel.selectCategory(it) },
              onSortSelected = { viewModel.setSortOption(it) },
              onToyClick = { viewModel.navigateTo(Screen.ProductDetail(it)) },
              onAddToCart = { viewModel.addToCart(it) }
            )
          }

          is Screen.Categories -> {
            CategoriesScreen(
              toys = allToys,
              urduProminent = urduProminent,
              onCategoryClick = { categoryId ->
                viewModel.selectCategory(categoryId)
                viewModel.navigateTo(Screen.Home)
              }
            )
          }

          is Screen.Cart -> {
            CartScreen(
              cartItems = cartItems,
              discountAmount = discountAmount,
              promoCode = promoCode,
              urduProminent = urduProminent,
              onUpdateQuantity = { item, newQty -> viewModel.updateCartItemQuantity(item, newQty) },
              onRemoveItem = { item -> viewModel.removeCartItem(item) },
              onClearCart = { viewModel.clearCart() },
              onApplyPromo = { code, subtotal -> viewModel.applyPromoCode(code, subtotal) },
              onProceedToCheckout = { viewModel.navigateTo(Screen.Checkout) },
              onStartShopping = { viewModel.navigateTo(Screen.Home) }
            )
          }

          is Screen.ProductDetail -> {
            ProductDetailScreen(
              toy = screen.toy,
              urduProminent = urduProminent,
              onBack = { viewModel.navigateBack() },
              onAddToCart = { toy, qty -> viewModel.addToCart(toy, qty) },
              onBuyNow = { toy, qty ->
                viewModel.addToCart(toy, qty)
                viewModel.navigateTo(Screen.Checkout)
              }
            )
          }

          is Screen.Checkout -> {
            val subtotal = cartItems.sumOf { it.pricePkr * it.quantity }
            val deliveryFee = if (subtotal >= 2500) 0 else 200
            CheckoutScreen(
              cartItems = cartItems,
              subtotal = subtotal,
              deliveryFee = deliveryFee,
              discount = discountAmount,
              urduProminent = urduProminent,
              onBack = { viewModel.navigateBack() },
              onPlaceOrder = { name, phone, city, address, notes ->
                viewModel.placeOrder(name, phone, city, address, notes) { newOrder ->
                  viewModel.navigateTo(Screen.OrderConfirmation(newOrder))
                }
              }
            )
          }

          is Screen.OrderConfirmation -> {
            OrderConfirmationScreen(
              order = screen.order,
              urduProminent = urduProminent,
              onViewOrders = { viewModel.navigateTo(Screen.OrdersHistory) },
              onContinueShopping = { viewModel.navigateTo(Screen.Home) }
            )
          }

          is Screen.OrdersHistory -> {
            OrdersHistoryScreen(
              orders = orders,
              urduProminent = urduProminent,
              onStartShopping = { viewModel.navigateTo(Screen.Home) }
            )
          }

          is Screen.Admin -> {
            AdminScreen(
              toys = allToys,
              orders = orders,
              totalRevenue = totalRevenue,
              urduProminent = urduProminent,
              onSaveToy = { id, titleEn, titleUr, category, price, origPrice, stock, descEn, descUr, age, badge, featured ->
                viewModel.saveToy(id, titleEn, titleUr, category, price, origPrice, stock, descEn, descUr, age, badge, featured)
              },
              onDeleteToy = { viewModel.deleteToy(it) },
              onUpdateStock = { toyId, newStock -> viewModel.updateStock(toyId, newStock) },
              onUpdateOrderStatus = { orderId, newStatus -> viewModel.updateOrderStatus(orderId, newStatus) },
              onResetSampleData = { viewModel.resetToSampleData() }
            )
          }
        }
      }
    }
  }
}
