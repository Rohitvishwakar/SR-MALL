package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ShopRepository
import com.example.model.CartItem
import com.example.model.Product
import com.example.model.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

class ShopViewModel : ViewModel() {

    val allProducts = ShopRepository.products
    val categories = ShopRepository.categories
    val reviews = ShopRepository.sampleReviews

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _selectedSort = MutableStateFlow(SortOption.FEATURED)
    val selectedSort = _selectedSort.asStateFlow()

    private val _wishlistIds = MutableStateFlow<Set<String>>(setOf("prod_1", "prod_4"))
    val wishlistIds = _wishlistIds.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(
        listOf(
            CartItem(product = allProducts[0], quantity = 1, selectedColorHex = 0xFF0F172A),
            CartItem(product = allProducts[1], quantity = 1, selectedColorHex = 0xFF1E293B)
        )
    )
    val cartItems = _cartItems.asStateFlow()

    private val _promoCodeInput = MutableStateFlow("")
    val promoCodeInput = _promoCodeInput.asStateFlow()

    private val _appliedDiscountPercent = MutableStateFlow(0)
    val appliedDiscountPercent = _appliedDiscountPercent.asStateFlow()

    private val _promoStatus = MutableStateFlow<String?>(null)
    val promoStatus = _promoStatus.asStateFlow()

    private val _selectedProductForDetail = MutableStateFlow<Product?>(null)
    val selectedProductForDetail = _selectedProductForDetail.asStateFlow()

    private val _isCheckoutOpen = MutableStateFlow(false)
    val isCheckoutOpen = _isCheckoutOpen.asStateFlow()

    private val _orderConfirmationId = MutableStateFlow<String?>(null)
    val orderConfirmationId = _orderConfirmationId.asStateFlow()

    val filteredProducts: StateFlow<List<Product>> = combine(
        _searchQuery,
        _selectedCategory,
        _selectedSort
    ) { query, category, sort ->
        var list = allProducts.filter { product ->
            val matchesCategory = (category == "all" || product.category.equals(category, ignoreCase = true))
            val matchesQuery = query.isBlank() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.brand.contains(query, ignoreCase = true) ||
                    product.shortDescription.contains(query, ignoreCase = true) ||
                    product.category.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }

        list = when (sort) {
            SortOption.FEATURED -> list
            SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.price }
            SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
            SortOption.HIGHEST_RATED -> list.sortedByDescending { it.rating }
        }
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = allProducts
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelect(categoryId: String) {
        _selectedCategory.value = categoryId
    }

    fun onSortSelect(sortOption: SortOption) {
        _selectedSort.value = sortOption
    }

    fun toggleWishlist(productId: String) {
        val current = _wishlistIds.value.toMutableSet()
        if (current.contains(productId)) {
            current.remove(productId)
        } else {
            current.add(productId)
        }
        _wishlistIds.value = current
    }

    fun isInWishlist(productId: String): Boolean {
        return _wishlistIds.value.contains(productId)
    }

    fun addToCart(product: Product, colorHex: Long = 0xFF0F172A, size: String? = null, qty: Int = 1) {
        val currentList = _cartItems.value.toMutableList()
        val existingIndex = currentList.indexOfFirst {
            it.product.id == product.id && it.selectedColorHex == colorHex && it.selectedSize == size
        }
        if (existingIndex >= 0) {
            val existing = currentList[existingIndex]
            currentList[existingIndex] = existing.copy(quantity = existing.quantity + qty)
        } else {
            currentList.add(CartItem(product, qty, colorHex, size))
        }
        _cartItems.value = currentList
    }

    fun updateCartQuantity(productId: String, delta: Int) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.product.id == productId }
        if (index >= 0) {
            val item = currentList[index]
            val newQty = item.quantity + delta
            if (newQty <= 0) {
                currentList.removeAt(index)
            } else {
                currentList[index] = item.copy(quantity = newQty)
            }
            _cartItems.value = currentList
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filterNot { it.product.id == productId }
    }

    fun setPromoCodeInput(code: String) {
        _promoCodeInput.value = code
    }

    fun applyPromoCode() {
        val code = _promoCodeInput.value.trim().uppercase()
        when (code) {
            "SHOPAI20" -> {
                _appliedDiscountPercent.value = 20
                _promoStatus.value = "Code applied! 20% discount added."
            }
            "SAVE10" -> {
                _appliedDiscountPercent.value = 10
                _promoStatus.value = "Code applied! 10% discount added."
            }
            "FREESHIP" -> {
                _appliedDiscountPercent.value = 5
                _promoStatus.value = "Free Express Shipping unlocked!"
            }
            else -> {
                _promoStatus.value = "Invalid coupon. Try 'SHOPAI20' for 20% off."
            }
        }
    }

    fun clearPromoCode() {
        _promoCodeInput.value = ""
        _appliedDiscountPercent.value = 0
        _promoStatus.value = null
    }

    fun openProductDetail(product: Product) {
        _selectedProductForDetail.value = product
    }

    fun closeProductDetail() {
        _selectedProductForDetail.value = null
    }

    fun openCheckout() {
        _isCheckoutOpen.value = true
    }

    fun closeCheckout() {
        _isCheckoutOpen.value = false
    }

    fun placeOrder(address: String, paymentMethod: String) {
        val orderId = "ORD-" + UUID.randomUUID().toString().take(8).uppercase()
        _cartItems.value = emptyList()
        _isCheckoutOpen.value = false
        _orderConfirmationId.value = orderId
    }

    fun dismissOrderConfirmation() {
        _orderConfirmationId.value = null
    }

    // Calculations
    val subtotal: Double
        get() = _cartItems.value.sumOf { it.product.price * it.quantity }

    val discountAmount: Double
        get() = subtotal * (_appliedDiscountPercent.value / 100.0)

    val shippingFee: Double
        get() = if (subtotal > 150.0 || _appliedDiscountPercent.value == 5) 0.0 else 9.99

    val finalTotal: Double
        get() = (subtotal - discountAmount + shippingFee).coerceAtLeast(0.0)

    val totalItemCount: Int
        get() = _cartItems.value.sumOf { it.quantity }
}
