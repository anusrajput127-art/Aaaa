package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CategoryInfo
import com.example.data.model.ToyCategories
import com.example.data.model.ToyItem
import com.example.ui.SortOption
import com.example.ui.components.ToyCard
import com.example.ui.theme.ToyBlueSecondary
import com.example.ui.theme.ToyGreenSuccess
import com.example.ui.theme.ToyRedPrimary
import com.example.ui.theme.ToyYellowTertiary

@Composable
fun HomeScreen(
  toys: List<ToyItem>,
  featuredToys: List<ToyItem>,
  searchQuery: String,
  selectedCategory: String,
  selectedSort: SortOption,
  urduProminent: Boolean,
  onSearchQueryChanged: (String) -> Unit,
  onCategorySelected: (String) -> Unit,
  onSortSelected: (SortOption) -> Unit,
  onToyClick: (ToyItem) -> Unit,
  onAddToCart: (ToyItem) -> Unit,
  modifier: Modifier = Modifier
) {
  var sortMenuExpanded by remember { mutableStateOf(false) }

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 12.dp),
    contentPadding = PaddingValues(bottom = 80.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // 1. Hero Promo Banner
    item(span = { GridItemSpan(2) }) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp, bottom = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ToyRedPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.img_toys_hero_banner),
            contentDescription = "Toys Mart Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )

          // Gradient overlay for high text legibility
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.horizontalGradient(
                  colors = listOf(
                    Color.Black.copy(alpha = 0.72f),
                    Color.Black.copy(alpha = 0.25f),
                    Color.Transparent
                  )
                )
              )
          )

          Column(
            modifier = Modifier
              .align(Alignment.CenterStart)
              .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 60.dp),
            verticalArrangement = Arrangement.Center
          ) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = ToyYellowTertiary
            ) {
              Text(
                text = "🎉 سال کا سب سے بڑا کھلونا میلہ",
                color = Color.Black,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Toys Mart Pakistan",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = Color.White
            )
            Text(
              text = "🚚 پورے پاکستان میں کیش آن ڈیلیوری دستیاب ہے",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color.White.copy(alpha = 0.95f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = ToyGreenSuccess,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Free Delivery on orders above Rs. 2,500",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = ToyGreenSuccess
              )
            }
          }
        }
      }
    }

    // 2. Search & Filter Bar
    item(span = { GridItemSpan(2) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChanged,
          placeholder = {
            Text(
              text = if (urduProminent) "کھلونا تلاش کریں... (Search toys)" else "Search toys, cars, dolls, blocks...",
              fontSize = 13.sp
            )
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = "Search",
              tint = ToyBlueSecondary
            )
          },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchQueryChanged("") }) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = "Clear search"
                )
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ToyBlueSecondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
          ),
          modifier = Modifier
            .weight(1f)
            .testTag("search_toys_input")
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Sort Button & Menu
        Box {
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
              .clip(RoundedCornerShape(14.dp))
              .clickable { sortMenuExpanded = true }
              .testTag("sort_menu_btn")
          ) {
            Box(
              modifier = Modifier.size(54.dp),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = "Sort toys",
                tint = ToyBlueSecondary
              )
            }
          }

          DropdownMenu(
            expanded = sortMenuExpanded,
            onDismissRequest = { sortMenuExpanded = false }
          ) {
            SortOption.values().forEach { option ->
              DropdownMenuItem(
                text = {
                  Text(
                    text = "${option.titleUr} (${option.titleEn})",
                    fontWeight = if (selectedSort == option) FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedSort == option) ToyRedPrimary else MaterialTheme.colorScheme.onSurface
                  )
                },
                onClick = {
                  onSortSelected(option)
                  sortMenuExpanded = false
                }
              )
            }
          }
        }
      }
    }

    // 3. Category Horizontal Filter Chips
    item(span = { GridItemSpan(2) }) {
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
      ) {
        items(ToyCategories.filterList) { category ->
          val isSelected = selectedCategory == category.id
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) category.themeColor else MaterialTheme.colorScheme.surface,
            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .clickable { onCategorySelected(category.id) }
              .testTag("category_chip_${category.id}")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = category.iconEmoji,
                fontSize = 14.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (urduProminent) category.nameUr else category.nameEn,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 12.sp
              )
            }
          }
        }
      }
    }

    // 4. Section Header: Toys List
    item(span = { GridItemSpan(2) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = if (urduProminent) "دستیاب کھلونے (Available Toys)" else "Our Toy Collection",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "${toys.size} items in stock",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant
        ) {
          Text(
            text = selectedSort.titleUr,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = ToyBlueSecondary,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }

    // 5. Empty State if no toys match filter/search
    if (toys.isEmpty()) {
      item(span = { GridItemSpan(2) }) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = "🔍", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "کوئی کھلونا نہیں ملا",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "No toys found matching \"$searchQuery\". Try checking another category or clearing search.",
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    } else {
      // 6. Toys Grid Items
      items(toys, key = { it.id }) { toy ->
        ToyCard(
          toy = toy,
          urduProminent = urduProminent,
          onClick = { onToyClick(toy) },
          onAddToCart = { onAddToCart(toy) }
        )
      }
    }
  }
}
