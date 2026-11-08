package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.CartDao
import com.example.data.dao.OrderDao
import com.example.data.dao.ToyDao
import com.example.data.model.CartItem
import com.example.data.model.OrderRecord
import com.example.data.model.ToyItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
  entities = [ToyItem::class, CartItem::class, OrderRecord::class],
  version = 1,
  exportSchema = false
)
abstract class ToysDatabase : RoomDatabase() {
  abstract fun toyDao(): ToyDao
  abstract fun cartDao(): CartDao
  abstract fun orderDao(): OrderDao

  companion object {
    @Volatile
    private var INSTANCE: ToysDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): ToysDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          ToysDatabase::class.java,
          "toys_mart_database"
        )
          .addCallback(ToysDatabaseCallback(scope))
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }

    private class ToysDatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            populateDatabase(database.toyDao(), database.orderDao())
          }
        }
      }

      suspend fun populateDatabase(toyDao: ToyDao, orderDao: OrderDao) {
        if (toyDao.getToyCount() == 0) {
          toyDao.insertToys(SampleToysData.getInitialToys())
          SampleToysData.getInitialOrders().forEach { order ->
            orderDao.insertOrder(order)
          }
        }
      }
    }
  }
}
