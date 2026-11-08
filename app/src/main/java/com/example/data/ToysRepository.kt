package com.example.data

import com.example.data.dao.CartDao
import com.example.data.dao.OrderDao
import com.example.data.dao.ToyDao
import com.example.data.model.CartItem
import com.example.data.model.OrderRecord
import com.example.data.model.ToyItem
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

class ToysRepository(
  private val toyDao: ToyDao,
  private val cartDao: CartDao,
  private val orderDao: OrderDao
) {
  val allToys: Flow<List<ToyItem>> = toyDao.getAllToys()
  val featuredToys: Flow<List<ToyItem>> = toyDao.getFeaturedToys()
  val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()
  val cartCount: Flow<Int?> = cartDao.getTotalCartCount()
  val allOrders: Flow<List<OrderRecord>> = orderDao.getAllOrders()
  val orderCount: Flow<Int> = orderDao.getOrderCount()
  val totalRevenue: Flow<Int?> = orderDao.getTotalRevenue()

  fun getToysByCategory(categoryId: String): Flow<List<ToyItem>> {
    return if (categoryId == "all") {
      toyDao.getAllToys()
    } else {
      toyDao.getToysByCategory(categoryId)
    }
  }

  suspend fun getToyById(id: Long): ToyItem? = toyDao.getToyById(id)

  suspend fun addToCart(toy: ToyItem, quantity: Int = 1) {
    val existing = cartDao.getCartItemByToyId(toy.id)
    if (existing != null) {
      val newQty = (existing.quantity + quantity).coerceAtMost(toy.stock)
      cartDao.updateCartItem(existing.copy(quantity = newQty))
    } else {
      cartDao.insertCartItem(
        CartItem(
          toyId = toy.id,
          titleEn = toy.titleEn,
          titleUr = toy.titleUr,
          categoryId = toy.categoryId,
          pricePkr = toy.pricePkr,
          quantity = quantity.coerceAtMost(toy.stock.coerceAtLeast(1)),
          stock = toy.stock,
          imageUrl = toy.imageUrl
        )
      )
    }
  }

  suspend fun updateCartQuantity(cartId: Long, newQuantity: Int) {
    if (newQuantity <= 0) {
      cartDao.deleteCartItemById(cartId)
    } else {
      val items = cartDao.getAllCartItems()
      // or direct update
      cartDao.updateCartItem(CartItem(cartId = cartId, toyId = 0, titleEn = "", titleUr = "", categoryId = "", pricePkr = 0, quantity = newQuantity))
    }
  }

  suspend fun updateCartItem(item: CartItem) {
    if (item.quantity <= 0) {
      cartDao.deleteCartItem(item)
    } else {
      cartDao.updateCartItem(item)
    }
  }

  suspend fun removeCartItem(item: CartItem) {
    cartDao.deleteCartItem(item)
  }

  suspend fun clearCart() {
    cartDao.clearCart()
  }

  suspend fun placeOrder(
    customerName: String,
    customerPhone: String,
    city: String,
    address: String,
    orderNotes: String,
    cartItems: List<CartItem>,
    subtotal: Int,
    deliveryFee: Int,
    discount: Int
  ): OrderRecord {
    val orderId = "TM-" + (10000 + Random.nextInt(90000))
    val itemsSummary = cartItems.joinToString(", ") { "${it.quantity}x ${it.titleEn}" }
    val totalCount = cartItems.sumOf { it.quantity }
    val totalAmount = subtotal + deliveryFee - discount

    val order = OrderRecord(
      orderId = orderId,
      customerName = customerName,
      customerPhone = customerPhone,
      city = city,
      address = address,
      orderNotes = orderNotes,
      paymentMethod = "Cash on Delivery (کیش آن ڈیلیوری)",
      itemsSummary = itemsSummary,
      totalItemsCount = totalCount,
      subtotalPkr = subtotal,
      deliveryFeePkr = deliveryFee,
      discountPkr = discount,
      totalAmountPkr = totalAmount,
      status = "Pending",
      timestamp = System.currentTimeMillis()
    )

    orderDao.insertOrder(order)

    // Deduct stock
    for (item in cartItems) {
      val toy = toyDao.getToyById(item.toyId)
      if (toy != null) {
        val newStock = (toy.stock - item.quantity).coerceAtLeast(0)
        toyDao.updateStock(toy.id, newStock)
      }
    }

    // Clear cart
    cartDao.clearCart()

    return order
  }

  // Admin Actions
  suspend fun insertToy(toy: ToyItem): Long = toyDao.insertToy(toy)
  suspend fun updateToy(toy: ToyItem) = toyDao.updateToy(toy)
  suspend fun deleteToy(toy: ToyItem) = toyDao.deleteToy(toy)
  suspend fun updateStock(toyId: Long, stock: Int) = toyDao.updateStock(toyId, stock)
  suspend fun updateOrderStatus(orderId: String, status: String) = orderDao.updateOrderStatus(orderId, status)

  suspend fun resetToSampleData() {
    cartDao.clearCart()
    toyDao.deleteAllToys()
    toyDao.insertToys(SampleToysData.getInitialToys())
  }
}
