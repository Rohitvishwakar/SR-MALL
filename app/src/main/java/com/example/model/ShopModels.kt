package com.example.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val originalPrice: Double,
    val rating: Double,
    val reviewCount: Int,
    @DrawableRes val imageRes: Int,
    val tag: String? = null,
    val shortDescription: String,
    val persuasiveDescription: String,
    val keyFeatures: List<String>,
    val benefits: List<String>,
    val availableColors: List<Long> = listOf(0xFF0F172A, 0xFF475569, 0xFFCBD5E1),
    val availableSizes: List<String> = emptyList(),
    val inStock: Boolean = true,
    val deliveryEstimate: String = "Free 2-Day Delivery"
) {
    val discountPercent: Int
        get() = if (originalPrice > price) {
            (((originalPrice - price) / originalPrice) * 100).toInt()
        } else 0
}

data class CustomerReview(
    val id: String,
    val author: String,
    val rating: Int,
    val date: String,
    val comment: String,
    val verifiedPurchase: Boolean = true
)

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val selectedColorHex: Long = 0xFF0F172A,
    val selectedSize: String? = null
)

data class CategoryItem(
    val id: String,
    val name: String,
    val iconName: String
)

enum class SortOption(val displayName: String) {
    FEATURED("Featured"),
    PRICE_LOW_HIGH("Price: Low to High"),
    PRICE_HIGH_LOW("Price: High to Low"),
    HIGHEST_RATED("Highest Rated")
}
