package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleToysData
import com.example.data.ToysDatabase
import com.example.data.ToysRepository
import com.example.data.model.CartItem
import com.example.data.model.CategoryInfo
import com.example.data.model.OrderRecord
import com.example.data.model.ToyCategories
import com.example.data.model.ToyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption(val titleEn: String, val titleUr: String) {
  FEATURED("Featured", "نمایاں"),
  PRICE_LOW("Price: Low to High", "قیمت: کم سے زیادہ"),
  PRICE_HIGH("Price: High to Low", "قیمت: زیادہ سے کم"),
  RATING("Top Rated", "بہترین ریٹنگ")
}

sealed class Screen {
  object Home : Screen()
  object Categories : Screen()
  object Cart : Screen()
  object OrdersHistory : Screen()
  object Admin : Screen()
  data class ProductDetail(val toy: ToyItem) : Screen()
  object Checkout : Screen()
  data class OrderConfirmation(val order: OrderRecord) : Screen()
}

class ToysMartViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: ToysRepository

  init {
    val db = ToysDatabase.getDatabase(application, viewModelScope)
    repository = ToysRepository(db.toyDao(), db.cartDao(), db.orderDao())
    // Ensure initial data is populated
    viewModelScope.launch {
      if (db.toyDao().getToyCount() == 0) {
        db.toyDao().insertToys(SampleToysData.getInitialToys())
        SampleToysData.getInitialOrders().forEach { db.orderDao().insertOrder(it) }
      }
    }
  }

  // Navigation state
  private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  // Selected Toy for Details
  private val _selectedToy = MutableStateFlow<ToyItem?>(null)
  val selectedToy: StateFlow<ToyItem?> = _selectedToy.asStateFlow()

  // Language display preference: Dual by default, or prefer Urdu prominent
  private val _urduProminent = MutableStateFlow(true)
  val urduProminent: StateFlow<Boolean> = _urduProminent.asStateFlow()

  // Filter & Search states
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedCategory = MutableStateFlow("all")
  val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

  private val _selectedSort = MutableStateFlow(SortOption.FEATURED)
  val selectedSort: StateFlow<SortOption> = _selectedSort.asStateFlow()

  // Toast / SnackBar feedback message
  private val _userMessage = MutableStateFlow<String?>(null)
  val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

  // Data streams
  val allToys: StateFlow<List<ToyItem>> = repository.allToys
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val featuredToys: StateFlow<List<ToyItem>> = repository.featuredToys
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val filteredToys: StateFlow<List<ToyItem>> = combine(
    allToys,
    _searchQuery,
    _selectedCategory,
    _selectedSort
  ) { toys, query, category, sort ->
    var result = toys
    if (category != "all") {
      result = result.filter { it.categoryId == category }
    }
    if (query.isNotBlank()) {
      val q = query.trim().lowercase()
      result = result.filter {
        it.titleEn.lowercase().contains(q) ||
            it.titleUr.contains(q) ||
            it.descriptionEn.lowercase().contains(q) ||
            it.descriptionUr.contains(q) ||
            it.categoryId.lowercase().contains(q)
      }
    }
    when (sort) {
      SortOption.FEATURED -> result.sortedByDescending { it.isFeatured }
      SortOption.PRICE_LOW -> result.sortedBy { it.pricePkr }
      SortOption.PRICE_HIGH -> result.sortedByDescending { it.pricePkr }
      SortOption.RATING -> result.sortedByDescending { it.rating }
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val cartItems: StateFlow<List<CartItem>> = repository.allCartItems
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val cartCount: StateFlow<Int> = repository.cartCount
    .combine(MutableStateFlow(0)) { count, _ -> count ?: 0 }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val orders: StateFlow<List<OrderRecord>> = repository.allOrders
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val totalRevenue: StateFlow<Int> = repository.totalRevenue
    .combine(MutableStateFlow(0)) { rev, _ -> rev ?: 0 }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  // Promo code & discount
  private val _promoCode = MutableStateFlow("")
  val promoCode: StateFlow<String> = _promoCode.asStateFlow()

  private val _discountAmount = MutableStateFlow(0)
  val discountAmount: StateFlow<Int> = _discountAmount.asStateFlow()

  // Navigation handlers
  fun navigateTo(screen: Screen) {
    if (screen is Screen.ProductDetail) {
      _selectedToy.value = screen.toy
    }
    _currentScreen.value = screen
  }

  fun navigateBack() {
    when (_currentScreen.value) {
      is Screen.ProductDetail -> _currentScreen.value = Screen.Home
      is Screen.Checkout -> _currentScreen.value = Screen.Cart
      is Screen.OrderConfirmation -> _currentScreen.value = Screen.OrdersHistory
      else -> _currentScreen.value = Screen.Home
    }
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun selectCategory(categoryId: String) {
    _selectedCategory.value = categoryId
  }

  fun setSortOption(sort: SortOption) {
    _selectedSort.value = sort
  }

  fun toggleUrduProminent() {
    _urduProminent.value = !_urduProminent.value
  }

  fun clearUserMessage() {
    _userMessage.value = null
  }

  // Cart operations
  fun addToCart(toy: ToyItem, quantity: Int = 1) {
    if (toy.stock <= 0) {
      _userMessage.value = "معذرت، یہ کھلونا ابھی ختم ہے! (Out of stock)"
      return
    }
    viewModelScope.launch {
      repository.addToCart(toy, quantity)
      _userMessage.value = "ٹوکری میں شامل کر دیا گیا! Added ${toy.titleEn} to cart"
    }
  }

  fun updateCartItemQuantity(item: CartItem, newQty: Int) {
    viewModelScope.launch {
      if (newQty <= 0) {
        repository.removeCartItem(item)
      } else {
        val cappedQty = newQty.coerceAtMost(item.stock)
        repository.updateCartItem(item.copy(quantity = cappedQty))
      }
    }
  }

  fun removeCartItem(item: CartItem) {
    viewModelScope.launch {
      repository.removeCartItem(item)
    }
  }

  fun clearCart() {
    viewModelScope.launch {
      repository.clearCart()
      _discountAmount.value = 0
      _promoCode.value = ""
    }
  }

  fun applyPromoCode(code: String, subtotal: Int) {
    val trimmed = code.trim().uppercase()
    _promoCode.value = trimmed
    if (trimmed == "TOYS10" || trimmed == "BACHPAN" || trimmed == "SALE") {
      val discount = (subtotal * 0.10).toInt()
      _discountAmount.value = discount
      _userMessage.value = "10% ڈسکاؤنٹ لاگو ہو گیا! Promo applied: Rs. $discount OFF"
    } else if (trimmed == "FREESHIP") {
      _discountAmount.value = 200
      _userMessage.value = "مفت ڈیلیوری ڈسکاؤنٹ! Free Shipping promo applied"
    } else {
      _discountAmount.value = 0
      _userMessage.value = "غیر درست پرومو کوڈ (Invalid Promo Code)"
    }
  }

  // Checkout & Order Placement
  fun placeOrder(
    customerName: String,
    customerPhone: String,
    city: String,
    address: String,
    orderNotes: String,
    onSuccess: (OrderRecord) -> Unit
  ) {
    val items = cartItems.value
    if (items.isEmpty()) {
      _userMessage.value = "آپ کی ٹوکری خالی ہے! (Your cart is empty)"
      return
    }

    val subtotal = items.sumOf { it.pricePkr * it.quantity }
    val deliveryFee = if (subtotal >= 2500) 0 else 200
    val discount = _discountAmount.value

    viewModelScope.launch {
      val order = repository.placeOrder(
        customerName = customerName,
        customerPhone = customerPhone,
        city = city,
        address = address,
        orderNotes = orderNotes,
        cartItems = items,
        subtotal = subtotal,
        deliveryFee = deliveryFee,
        discount = discount
      )
      _discountAmount.value = 0
      _promoCode.value = ""
      _userMessage.value = "مبارک ہو! آپ کا آرڈر درج ہو گیا (Order Placed Successfully)"
      onSuccess(order)
    }
  }

  // Admin Actions
  fun saveToy(
    id: Long,
    titleEn: String,
    titleUr: String,
    category: String,
    pricePkr: Int,
    originalPricePkr: Int?,
    stock: Int,
    descriptionEn: String,
    descriptionUr: String,
    ageRange: String,
    badgeText: String,
    isFeatured: Boolean
  ) {
    viewModelScope.launch {
      val toy = ToyItem(
        id = id,
        titleEn = titleEn,
        titleUr = if (titleUr.isBlank()) titleEn else titleUr,
        categoryId = category,
        pricePkr = pricePkr,
        originalPricePkr = originalPricePkr,
        stock = stock,
        descriptionEn = descriptionEn,
        descriptionUr = if (descriptionUr.isBlank()) descriptionEn else descriptionUr,
        ageRange = ageRange,
        isFeatured = isFeatured,
        badgeText = badgeText
      )
      if (id == 0L) {
        repository.insertToy(toy)
        _userMessage.value = "نیا کھلونا شامل کر دیا گیا! New toy added"
      } else {
        repository.updateToy(toy)
        _userMessage.value = "کھلونا اپ ڈیٹ کر دیا گیا! Toy updated"
      }
    }
  }

  fun deleteToy(toy: ToyItem) {
    viewModelScope.launch {
      repository.deleteToy(toy)
      _userMessage.value = "کھلونا حذف کر دیا گیا! Toy deleted"
    }
  }

  fun updateStock(toyId: Long, newStock: Int) {
    viewModelScope.launch {
      repository.updateStock(toyId, newStock)
    }
  }

  fun updateOrderStatus(orderId: String, newStatus: String) {
    viewModelScope.launch {
      repository.updateOrderStatus(orderId, newStatus)
      _userMessage.value = "آرڈر کی حالت اپ ڈیٹ ہو گئی! Order status changed to $newStatus"
    }
  }

  fun resetToSampleData() {
    viewModelScope.launch {
      repository.resetToSampleData()
      _userMessage.value = "اسٹور ڈیٹا دوبارہ سیٹ کر دیا گیا! Reset to sample toys"
    }
  }
}
