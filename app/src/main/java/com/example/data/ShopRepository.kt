package com.example.data

import com.example.R
import com.example.model.CategoryItem
import com.example.model.CustomerReview
import com.example.model.Product

object ShopRepository {

    val categories = listOf(
        CategoryItem("all", "All", "explore"),
        CategoryItem("audio", "Audio", "headphones"),
        CategoryItem("watches", "Watches", "watch"),
        CategoryItem("footwear", "Footwear", "sneakers"),
        CategoryItem("lifestyle", "Lifestyle", "bag"),
        CategoryItem("home", "Home Decor", "lamp")
    )

    val products = listOf(
        Product(
            id = "prod_1",
            name = "AeroSound Pro ANC Wireless",
            brand = "AeroAcoustics",
            category = "audio",
            price = 199.99,
            originalPrice = 279.99,
            rating = 4.9,
            reviewCount = 1240,
            imageRes = R.drawable.prod_headphones,
            tag = "Best Seller",
            shortDescription = "Studio-grade active noise cancellation with 45h battery life and ultra-plush memory foam.",
            persuasiveDescription = "Immerse yourself in concert-hall clarity. Engineered with aerospace acoustic dampening and customized 40mm beryllium drivers, AeroSound Pro isolates you from ambient distractions so you can focus on pure acoustic fidelity.",
            keyFeatures = listOf(
                "Adaptive Hybrid Active Noise Cancellation (42dB reduction)",
                "Up to 45 hours battery life with USB-C Quick Charge (10 min = 5 hours)",
                "Multipoint Bluetooth 5.3 seamlessly switches between phone and laptop",
                "Ergonomic cloud-foam ear cushions wrapped in breathable vegan leather"
            ),
            benefits = listOf(
                "Crystal-clear calls even in windy outdoor settings",
                "Zero ear fatigue during long flights or work sessions",
                "High-Resolution LDAC lossless audio decoding"
            ),
            availableColors = listOf(0xFF0F172A, 0xFF64748B, 0xFFE2E8F0)
        ),
        Product(
            id = "prod_2",
            name = "Chronos Titanium Horizon Smartwatch",
            brand = "Chronos Tech",
            category = "watches",
            price = 249.50,
            originalPrice = 329.00,
            rating = 4.8,
            reviewCount = 890,
            imageRes = R.drawable.prod_watch,
            tag = "Trending Pick",
            shortDescription = "Grade 5 titanium aerospace case, AMOLED sapphire crystal, 14-day battery.",
            persuasiveDescription = "A masterpiece of modern horology and intelligent biometrics. Built for boardroom sophistication and wilderness endurance, Chronos Horizon tracks advanced heart rate, SpO2, sleep architecture, and GPS precision without requiring daily charging.",
            keyFeatures = listOf(
                "Aerospace Grade-5 brushed titanium bezel with sapphire crystal glass",
                "1.43-inch Always-On Retina AMOLED display (1000 nits daylight brightness)",
                "Up to 14 days typical battery endurance on a single magnetic charge",
                "Dual-band 5-satellite GNSS positioning with turn-by-turn navigation"
            ),
            benefits = listOf(
                "Scratches and water resistant up to 50 meters (5 ATM)",
                "Comprehensive 24/7 health and stress monitoring",
                "Interchangeable quick-release Italian leather strap included"
            ),
            availableColors = listOf(0xFF1E293B, 0xFF78350F, 0xFF334155)
        ),
        Product(
            id = "prod_3",
            name = "Lumina Scandinavian Minimalist Desk Lamp",
            brand = "Nordic Living",
            category = "home",
            price = 89.00,
            originalPrice = 120.00,
            rating = 4.7,
            reviewCount = 420,
            imageRes = R.drawable.prod_lamp,
            tag = "New Arrival",
            shortDescription = "Warm ambient architectural task lighting with touch dimming and wireless Qi charging pad.",
            persuasiveDescription = "Elevate your work sanctuary with calm, flicker-free Nordic illumination. Lumina mimics natural daylight cycle to reduce eye strain, while the integrated Qi charging base powers your smartphone without cord clutter.",
            keyFeatures = listOf(
                "Warm 2700K - 5000K circadian color temperature selector",
                "Seamless continuous touch capacitive dimming slider",
                "Integrated 15W Qi wireless fast-charging baseplate",
                "Solid anodized aluminum architecture with matte sandblasted finish"
            ),
            benefits = listOf(
                "Eliminates blue-light glare and screen reflections",
                "Cleans your desk of tangled charging cables",
                "Aesthetic statement piece that complements modern interior decor"
            ),
            availableColors = listOf(0xFFF1F5F9, 0xFF0F172A, 0xFFD97706)
        ),
        Product(
            id = "prod_4",
            name = "CloudStrider Zero-Gravity Knit Sneaker",
            brand = "Strider Athletics",
            category = "footwear",
            price = 139.00,
            originalPrice = 175.00,
            rating = 4.9,
            reviewCount = 2150,
            imageRes = R.drawable.prod_sneakers,
            tag = "Hot Deal",
            shortDescription = "Featherlight breathable knit upper with responsive supercritical nitrogen foam midsole.",
            persuasiveDescription = "Engineered for endless urban exploration. CloudStrider combines ultra-breathable recycled knit architecture with nitrogen-infused foam to deliver bounce and energy return on every single step.",
            keyFeatures = listOf(
                "Supercritical nitrogen-infused rebound midsole (68% energy return)",
                "Engineered seamless recycled matrix knit with dynamic arch support",
                "High-traction continental rubber lug outsole for wet surfaces",
                "Ultra-lightweight: only 215 grams per shoe"
            ),
            benefits = listOf(
                "Relieves heel and joint impact throughout 10,000+ daily steps",
                "Machine washable removable anti-odor memory foam insole",
                "Sock-like fit that slips on effortlessly"
            ),
            availableColors = listOf(0xFFFFFFFF, 0xFF64748B, 0xFF0F172A),
            availableSizes = listOf("US 8", "US 9", "US 10", "US 11", "US 12")
        ),
        Product(
            id = "prod_5",
            name = "Heritage Artisan Full-Grain Leather Pack",
            brand = "Atelier & Co.",
            category = "lifestyle",
            price = 179.00,
            originalPrice = 220.00,
            rating = 4.8,
            reviewCount = 630,
            imageRes = R.drawable.prod_bag,
            tag = "Handcrafted",
            shortDescription = "Vegetable-tanned full-grain leather with padded 16-inch laptop compartment and brass hardware.",
            persuasiveDescription = "Built to age gracefully for a lifetime of journeys. Handcrafted from top-tier vegetable-tanned leather that develops a rich, individualized patina over time. Ample room for laptop, documents, tech accessories, and daily essentials.",
            keyFeatures = listOf(
                "100% full-grain vegetable-tanned Tuscan leather",
                "Shockproof quilted sleeve fits laptops up to 16 inches",
                "Heavy-duty solid antique brass YKK zippers and rivets",
                "Ergonomic padded shoulder straps with breathable mesh underside"
            ),
            benefits = listOf(
                "Grows more beautiful and distinguished with every year of use",
                "Water-resistant wax finish protects against rain showers",
                "Rear trolley strap easily slides over rolling luggage handles"
            ),
            availableColors = listOf(0xFF92400E, 0xFF1E293B, 0xFF451A03)
        )
    )

    val sampleReviews = listOf(
        CustomerReview(
            id = "rev_1",
            author = "Devon Vance",
            rating = 5,
            date = "2 days ago",
            comment = "Outstanding build quality and the active noise cancellation completely silences subway commutes. Fast shipping and arrived in pristine packaging!",
            verifiedPurchase = true
        ),
        CustomerReview(
            id = "rev_2",
            author = "Priya Sharma",
            rating = 5,
            date = "1 week ago",
            comment = "The finish and materials look way more expensive than the price tag. Customer support answered my sizing question in minutes. 10/10 recommend!",
            verifiedPurchase = true
        ),
        CustomerReview(
            id = "rev_3",
            author = "Marcus Thorne",
            rating = 4,
            date = "2 weeks ago",
            comment = "Super lightweight and comfortable for all-day wear. The battery life actually lives up to the manufacturer claims.",
            verifiedPurchase = true
        )
    )
}
