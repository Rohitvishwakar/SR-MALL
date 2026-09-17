package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.CheckoutDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderSuccessDialog
import com.example.ui.screens.ProductDetailModal
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.screens.CartScreen
import com.example.ui.theme.PrimaryAccent
import com.example.viewmodel.ShopViewModel
import kotlinx.coroutines.launch

enum class ShopTab(
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home),
    CATALOG("Explore", Icons.Filled.GridView, Icons.Outlined.GridView),
    WISHLIST("Saved", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    CART("Bag", Icons.Filled.ShoppingBag, Icons.Outlined.ShoppingBag),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun ShopApp(
    viewModel: ShopViewModel = viewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val selectedProduct by viewModel.selectedProductForDetail.collectAsState()
    val isCheckoutOpen by viewModel.isCheckoutOpen.collectAsState()
    val orderId by viewModel.orderConfirmationId.collectAsState()
    val cartCount = viewModel.totalItemCount
    val wishlistCount = viewModel.wishlistIds.collectAsState().value.size

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_navigation_bar")
            ) {
                ShopTab.values().forEachIndexed { index, tab ->
                    val isSelected = selectedTabIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            if (tab == ShopTab.CART && cartCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = PrimaryAccent,
                                            contentColor = Color.White
                                        ) {
                                            Text(text = "$cartCount", fontSize = 10.sp)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) tab.filledIcon else tab.outlinedIcon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            } else if (tab == ShopTab.WISHLIST && wishlistCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = Color.White
                                        ) {
                                            Text(text = "$wishlistCount", fontSize = 10.sp)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) tab.filledIcon else tab.outlinedIcon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = if (isSelected) tab.filledIcon else tab.outlinedIcon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryAccent,
                            selectedTextColor = PrimaryAccent,
                            indicatorColor = PrimaryAccent.copy(alpha = 0.12f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (ShopTab.values()[selectedTabIndex]) {
                ShopTab.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToCatalog = { selectedTabIndex = 1 },
                        onNavigateToCart = { selectedTabIndex = 3 },
                        onNavigateToWishlist = { selectedTabIndex = 2 },
                        onProductClick = { viewModel.openProductDetail(it) }
                    )
                }
                ShopTab.CATALOG -> {
                    CatalogScreen(
                        viewModel = viewModel,
                        onProductClick = { viewModel.openProductDetail(it) }
                    )
                }
                ShopTab.WISHLIST -> {
                    WishlistScreen(
                        viewModel = viewModel,
                        onProductClick = { viewModel.openProductDetail(it) },
                        onExploreClick = { selectedTabIndex = 1 }
                    )
                }
                ShopTab.CART -> {
                    CartScreen(
                        viewModel = viewModel,
                        onExploreClick = { selectedTabIndex = 1 },
                        onProceedCheckout = { viewModel.openCheckout() }
                    )
                }
                ShopTab.PROFILE -> {
                    ProfileScreen(
                        viewModel = viewModel,
                        onNavigateToWishlist = { selectedTabIndex = 2 }
                    )
                }
            }

            // Product Detail Sheet
            selectedProduct?.let { product ->
                val isInWishlist = viewModel.isInWishlist(product.id)
                ProductDetailModal(
                    product = product,
                    isWishlisted = isInWishlist,
                    onDismiss = { viewModel.closeProductDetail() },
                    onWishlistToggle = { viewModel.toggleWishlist(product.id) },
                    onAddToCart = { colorHex, size ->
                        viewModel.addToCart(product, colorHex, size)
                        viewModel.closeProductDetail()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Added \"${product.name}\" to your bag")
                        }
                    },
                    onBuyNow = { colorHex, size ->
                        viewModel.addToCart(product, colorHex, size)
                        viewModel.closeProductDetail()
                        viewModel.openCheckout()
                    }
                )
            }

            // Checkout Dialog
            if (isCheckoutOpen) {
                CheckoutDialog(
                    totalAmount = viewModel.finalTotal,
                    onDismiss = { viewModel.closeCheckout() },
                    onConfirmOrder = { address, paymentMethod ->
                        viewModel.placeOrder(address, paymentMethod)
                    }
                )
            }

            // Order Confirmation Dialog
            orderId?.let { confirmedId ->
                OrderSuccessDialog(
                    orderId = confirmedId,
                    onDismiss = {
                        viewModel.dismissOrderConfirmation()
                        selectedTabIndex = 0
                    }
                )
            }
        }
    }
}
