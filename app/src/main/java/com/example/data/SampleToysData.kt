package com.example.data

import com.example.data.model.OrderRecord
import com.example.data.model.ToyItem

object SampleToysData {
  fun getInitialToys(): List<ToyItem> {
    return listOf(
      // 1. TOY CARS (کھلونا گاڑیاں)
      ToyItem(
        id = 1,
        titleEn = "High-Speed RC 4WD Monster Truck",
        titleUr = "ہائی اسپیڈ ریموٹ کنٹرول 4WD مونسٹر ٹرک",
        categoryId = "cars",
        pricePkr = 2750,
        originalPricePkr = 3400,
        stock = 14,
        descriptionEn = "All-terrain remote control racing truck with shock-absorbing suspension, heavy-duty rubber tires, rechargeable battery and 2.4GHz remote controller.",
        descriptionUr = "ہر قسم کے راستوں کے لیے ریموٹ کنٹرول ریسنگ ٹرک، جھٹکے برداشت کرنے والی سسپنشن اور ریچارج ایبل بیٹری کے ساتھ۔",
        ageRange = "6-14 Years",
        rating = 4.9f,
        reviewCount = 38,
        isFeatured = true,
        badgeText = "Best Seller (بہترین فروخت)",
        featuresList = "2.4GHz Remote Control, Shock Absorber Springs, Rechargeable Li-ion Battery, Top Speed 20 km/h"
      ),
      ToyItem(
        id = 2,
        titleEn = "Die-Cast Metal Police Patrol Cruiser",
        titleUr = "دھاتی پولیس پیٹرول کار لائٹ اور سائرن کے ساتھ",
        categoryId = "cars",
        pricePkr = 1150,
        originalPricePkr = 1450,
        stock = 22,
        descriptionEn = "1:32 scale die-cast zinc alloy model with openable doors, flashing LED siren lights and realistic engine sounds when pressed.",
        descriptionUr = "کھلنے والے دروازے، چمکتی ہوئی سائرن لائٹس اور اصلی انجن کی آواز کے ساتھ اعلی معیار کی پولیس کار۔",
        ageRange = "3-10 Years",
        rating = 4.7f,
        reviewCount = 19,
        isFeatured = false,
        badgeText = "Sound & Light",
        featuresList = "Pull-back Action, Real Siren Sound, Openable Doors, Heavy Die-cast Metal Body"
      ),
      ToyItem(
        id = 3,
        titleEn = "Double Loop Turbo Racing Track Set",
        titleUr = "ڈبل لوپ ٹربو اسپیڈ ریسنگ ٹریک سیٹ",
        categoryId = "cars",
        pricePkr = 3200,
        originalPricePkr = 3990,
        stock = 8,
        descriptionEn = "Exciting 360-degree double vertical loop track with high-speed spring launcher and 2 metal race cars included.",
        descriptionUr = "360 ڈگری ڈبل لوپ ریسنگ ٹریک، تیز رفتار اسپرنگ لانچر اور 2 دھاتی ریسنگ کاروں کے ساتھ۔",
        ageRange = "4-12 Years",
        rating = 4.8f,
        reviewCount = 27,
        isFeatured = true,
        badgeText = "Popular Track",
        featuresList = "Double 360 Loop, 2 Metal Cars Included, Easy Snap-lock Track Assembly, No Batteries Required"
      ),

      // 2. DOLLS (گڑیا)
      ToyItem(
        id = 4,
        titleEn = "Royal Princess Fashion Doll with Wardrobe",
        titleUr = "شاہی شہزادی فیشن گڑیا 5 خوبصورت لباس کے ساتھ",
        categoryId = "dolls",
        pricePkr = 1850,
        originalPricePkr = 2300,
        stock = 18,
        descriptionEn = "Articulated fashion princess doll with silky brushable hair, tiara crown, 5 interchangeable party dresses and matching shoe accessories.",
        descriptionUr = "ریشمی بالوں، تاج، 5 خوبصورت لباسوں اور جوتوں کے ساتھ بچوں کی پسندیدہ فیشن ڈول۔",
        ageRange = "3-9 Years",
        rating = 4.8f,
        reviewCount = 45,
        isFeatured = true,
        badgeText = "5 Dresses Gift",
        featuresList = "Silky Combable Hair, Bendable Joints, 5 Designer Gowns, Mirror and Hairbrush Included"
      ),
      ToyItem(
        id = 5,
        titleEn = "Sweet Baby Talking & Crying Doll",
        titleUr = "آوازیں نکالنے اور رونے والی پیاری بے بی ڈول",
        categoryId = "dolls",
        pricePkr = 2450,
        originalPricePkr = 2999,
        stock = 11,
        descriptionEn = "Interactive 16-inch baby doll with feeding bottle and pacifier. Says Mama, Papa, laughs and giggles when tummy is pressed.",
        descriptionUr = "فیڈنگ بوتل اور چوسنی کے ساتھ، پیٹ دبانے پر ماما پاپا بولنے اور ہنسنے والی انٹرایکٹو بے بی گڑیا۔",
        ageRange = "2-7 Years",
        rating = 4.7f,
        reviewCount = 31,
        isFeatured = false,
        badgeText = "Interactive",
        featuresList = "12 Baby Sounds, Soft Huggable Body, Milk Bottle & Pacifier, Removable Washable Outfit"
      ),

      // 3. TEDDY BEARS (ٹیڈی بیئر)
      ToyItem(
        id = 6,
        titleEn = "Jumbo Soft Cuddle Teddy Bear (65cm)",
        titleUr = "جمبو سائز انتہائی نرم ٹیڈی بیئر (65 سینٹی میٹر)",
        categoryId = "teddies",
        pricePkr = 2890,
        originalPricePkr = 3500,
        stock = 9,
        descriptionEn = "Extra large ultra-plush cuddly teddy bear with satin bow tie, premium non-allergenic cotton filling and velvety soft fur.",
        descriptionUr = "نہایت نرم مخملی ٹیڈی بیئر، ساٹن بو ٹائی اور الرجی سے پاک روئی سے بھرا ہوا بہترین تحفہ۔",
        ageRange = "All Ages",
        rating = 5.0f,
        reviewCount = 62,
        isFeatured = true,
        badgeText = "Gift Favorite (بہترین تحفہ)",
        featuresList = "65cm Extra Large Size, 100% Hypoallergenic PP Cotton, Satin Neck Bow, Easy Surface Washable"
      ),
      ToyItem(
        id = 7,
        titleEn = "Musical Glow Night-Light Teddy Bear",
        titleUr = "رات کو چمکنے والا اور لوری سنانے والا ٹیڈی بیئر",
        categoryId = "teddies",
        pricePkr = 2100,
        originalPricePkr = 2600,
        stock = 15,
        descriptionEn = "Soothing bedtime companion with gentle rainbow LED glow and calm slumber melodies to comfort children to sleep.",
        descriptionUr = "رات کے وقت پرسکون رنگین روشنی اور لوری کی دھنوں کے ساتھ بچوں کی میٹھی نیند کا ساتھی۔",
        ageRange = "1-6 Years",
        rating = 4.9f,
        reviewCount = 28,
        isFeatured = false,
        badgeText = "Bedtime Soother",
        featuresList = "Gentle Rainbow Glow, 8 Lullaby Melodies, Auto-off Timer, Soft Velvet Fabric"
      ),

      // 4. EDUCATIONAL TOYS (تعلیمی کھلونے)
      ToyItem(
        id = 8,
        titleEn = "Wooden Urdu & English Haroof Alphabet Board",
        titleUr = "لکڑی کا اردو حروف تہجی اور انگریزی الفابیٹ بورڈ",
        categoryId = "educational",
        pricePkr = 1250,
        originalPricePkr = 1600,
        stock = 25,
        descriptionEn = "Natural pine wood educational puzzle board featuring Urdu Haroof-e-Tahajji and English A-Z with bright child-safe non-toxic paints.",
        descriptionUr = "قدرتی لکڑی سے تیار کردہ اردو حروف تہجی اور انگریزی حروف کا تعلیمی پزل بورڈ محفوظ رنگوں کے ساتھ۔",
        ageRange = "2-6 Years",
        rating = 4.9f,
        reviewCount = 54,
        isFeatured = true,
        badgeText = "Bilingual Learning (اردو و انگریزی)",
        featuresList = "Complete Urdu Alphabet, English A-Z, Smooth Polished Wood Edges, Non-toxic Organic Paint"
      ),
      ToyItem(
        id = 9,
        titleEn = "Junior Scientist Optical Microscope 1200x Kit",
        titleUr = "نوجوان سائنسدانوں کے لیے 1200x مائیکروسکوپ کٹ",
        categoryId = "educational",
        pricePkr = 3450,
        originalPricePkr = 4200,
        stock = 7,
        descriptionEn = "Discover biology and microbiology with adjustable magnification up to 1200x, LED illuminator, specimen slides and tweezers.",
        descriptionUr = "1200 گنا بڑی تصویر دکھانے والی مائیکروسکوپ، سلائیڈز اور آلات کے ساتھ اسکول کے طلبا کے لیے بہترین۔",
        ageRange = "8-15 Years",
        rating = 4.8f,
        reviewCount = 33,
        isFeatured = false,
        badgeText = "STEM Education",
        featuresList = "100x/400x/1200x Zoom, Dual LED Lighting, 5 Prepared Slides + Blank Slides, Tweezers & Tools"
      ),

      // 5. BABY TOYS (چھوٹے بچوں کے کھلونے)
      ToyItem(
        id = 10,
        titleEn = "7-in-1 Baby Musical Activity Cube",
        titleUr = "7 ان 1 بے بی میوزیکل ایکٹیویٹی کیوب کھلونہ",
        categoryId = "baby",
        pricePkr = 2350,
        originalPricePkr = 2850,
        stock = 12,
        descriptionEn = "Multi-sided sensory learning cube with piano keys, shape sorter, spinning gears, telephone dial, beads maze and clock hands.",
        descriptionUr = "پیانو بٹن، اشکال کی پہچان، گھومنے والے گیئرز اور فون ڈائل کے ساتھ بچوں کی دماغی نشوونما کا کیوب۔",
        ageRange = "6-24 Months",
        rating = 4.9f,
        reviewCount = 41,
        isFeatured = true,
        badgeText = "Early Development",
        featuresList = "Do-Re-Mi Musical Piano, Shape Sorter Maze, Rotating Gears, BPA Free & Rounded Edges"
      ),
      ToyItem(
        id = 11,
        titleEn = "Silicone Teething Rattle & Sensory Ball Set",
        titleUr = "فوڈ گریڈ سلیکون ٹیتھر اور ریمبو جھنجھنا سیٹ",
        categoryId = "baby",
        pricePkr = 850,
        originalPricePkr = 1100,
        stock = 30,
        descriptionEn = "BPA-free 100% food grade silicone soothing chew toys for teething gums with gentle rattling chimes to engage hearing and motor skills.",
        descriptionUr = "مسوڑھوں کو آرام دینے کے لیے محفوظ فوڈ گریڈ سلیکون جھنجھنا اور چبانے والے کھلونوں کا سیٹ۔",
        ageRange = "0-12 Months",
        rating = 4.8f,
        reviewCount = 22,
        isFeatured = false,
        badgeText = "BPA Free (محفوظ)",
        featuresList = "100% Food-Grade Silicone, Sterilizer Safe, Textured Gum Massage, Rattle Sound Beads"
      ),

      // 6. BUILDING BLOCKS (تعمیراتی بلاکس)
      ToyItem(
        id = 12,
        titleEn = "Mega 120-Piece Castle & City Builder Bricks",
        titleUr = "میگا 120 پیس قلعہ اور شہر بنانے والے رنگین بلاکس",
        categoryId = "blocks",
        pricePkr = 1950,
        originalPricePkr = 2490,
        stock = 16,
        descriptionEn = "Vibrant multi-colored interlocking construction bricks with storage tub. Compatible with major standard building brick sets.",
        descriptionUr = "بچوں کی تخلیقی صلاحیت بڑھانے والے 120 رنگ برنگے بلاکس، پائیدار اسٹوریج باکس کے ساتھ۔",
        ageRange = "3-10 Years",
        rating = 4.9f,
        reviewCount = 57,
        isFeatured = true,
        badgeText = "120 Pieces",
        featuresList = "120 Sturdy ABS Bricks, Portable Storage Box with Handle, Castle & Vehicle Guidebook"
      ),
      ToyItem(
        id = 13,
        titleEn = "3D Magnetic Building Tiles Deluxe Set (60 Pcs)",
        titleUr = "3D مقناطیسی بلڈنگ ٹائلز ڈیلکس سیٹ (60 ٹکڑے)",
        categoryId = "blocks",
        pricePkr = 3100,
        originalPricePkr = 3800,
        stock = 10,
        descriptionEn = "Translucent rainbow magnetic geometric tiles that click together effortlessly to build 3D pyramids, towers, castles, and rockets.",
        descriptionUr = "طاقتور مقناطیس والی شفاف رنگین ٹائلز جن سے بچے آسانی کے ساتھ 3D عمارتیں اور راکٹ بنا سکتے ہیں۔",
        ageRange = "3-12 Years",
        rating = 5.0f,
        reviewCount = 39,
        isFeatured = false,
        badgeText = "Strong Magnets",
        featuresList = "60 Magnetic Geometric Tiles, Ultrasonic Welding, Safe Riveted Corners, Idea Book Included"
      )
    )
  }

  fun getInitialOrders(): List<OrderRecord> {
    return listOf(
      OrderRecord(
        orderId = "TM-94021",
        customerName = "Muhammad Ali Khan",
        customerPhone = "0301-8472910",
        city = "Lahore (لاہور)",
        address = "House 42-B, Block G, Johar Town",
        orderNotes = "Please deliver after 2 PM",
        paymentMethod = "Cash on Delivery (کیش آن ڈیلیوری)",
        itemsSummary = "1x High-Speed RC 4WD Monster Truck, 1x Mega 120-Piece Blocks",
        totalItemsCount = 2,
        subtotalPkr = 4700,
        deliveryFeePkr = 0,
        discountPkr = 0,
        totalAmountPkr = 4700,
        status = "Shipped",
        timestamp = System.currentTimeMillis() - 86400000L * 2
      ),
      OrderRecord(
        orderId = "TM-94022",
        customerName = "Fatima Zahra",
        customerPhone = "0321-4920183",
        city = "Karachi (کراچی)",
        address = "Flat 304, Al-Madina Heights, Clifton Block 5",
        orderNotes = "Call before arrival",
        paymentMethod = "Cash on Delivery (کیش آن ڈیلیوری)",
        itemsSummary = "1x Royal Princess Fashion Doll, 1x Jumbo Soft Cuddle Teddy Bear",
        totalItemsCount = 2,
        subtotalPkr = 4740,
        deliveryFeePkr = 0,
        discountPkr = 200,
        totalAmountPkr = 4540,
        status = "Confirmed",
        timestamp = System.currentTimeMillis() - 86400000L
      ),
      OrderRecord(
        orderId = "TM-94023",
        customerName = "Zubair Ahmed",
        customerPhone = "0333-5192837",
        city = "Islamabad (اسلام آباد)",
        address = "House 18, Street 44, Sector F-8/1",
        orderNotes = "Leave at reception if not available",
        paymentMethod = "Cash on Delivery (کیش آن ڈیلیوری)",
        itemsSummary = "1x Wooden Urdu & English Haroof Alphabet Board",
        totalItemsCount = 1,
        subtotalPkr = 1250,
        deliveryFeePkr = 200,
        discountPkr = 0,
        totalAmountPkr = 1450,
        status = "Pending",
        timestamp = System.currentTimeMillis() - 3600000L * 4
      )
    )
  }
}
