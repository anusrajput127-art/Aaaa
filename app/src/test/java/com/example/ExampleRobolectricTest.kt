package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.SampleToysData
import com.example.data.model.ToyCategories
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Toys Mart", appName)
  }

  @Test
  fun `verify initial toys cover all requested categories`() {
    val toys = SampleToysData.getInitialToys()
    assertTrue(toys.isNotEmpty())
    val categories = toys.map { it.categoryId }.toSet()
    assertTrue(categories.contains("cars"))
    assertTrue(categories.contains("dolls"))
    assertTrue(categories.contains("teddies"))
    assertTrue(categories.contains("educational"))
    assertTrue(categories.contains("baby"))
    assertTrue(categories.contains("blocks"))
  }
}
