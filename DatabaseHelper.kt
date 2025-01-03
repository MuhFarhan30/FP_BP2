package com.example.fp_bp2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.fp_bp2.models.WishlistItem

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "wishlist.db"
        const val DATABASE_VERSION = 2

        const val TABLE_WISHLIST = "wishlist"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PRICE = "price"
        const val COLUMN_SIZE = "size"
        const val COLUMN_IMAGE_RES_ID = "image_res_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_WISHLIST (
                $COLUMN_ITEM_NAME TEXT NOT NULL,
                $COLUMN_QUANTITY INTEGER DEFAULT 1,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_PRICE REAL NOT NULL,
                $COLUMN_SIZE INTEGER NOT NULL,
                $COLUMN_IMAGE_RES_ID INTEGER DEFAULT -1,
                PRIMARY KEY ($COLUMN_ITEM_NAME, $COLUMN_SIZE)
            )
            """.trimIndent()
        )

        // Menambahkan indeks untuk performa
        db.execSQL("CREATE INDEX idx_item_name ON $TABLE_WISHLIST($COLUMN_ITEM_NAME)")
        db.execSQL("CREATE INDEX idx_size ON $TABLE_WISHLIST($COLUMN_SIZE)")

        Log.d("DatabaseHelper", "Database created successfully.")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("DELETE FROM $TABLE_WISHLIST WHERE $COLUMN_SIZE < 39 OR $COLUMN_SIZE > 43")
            Log.d("DatabaseHelper", "Database upgraded and invalid data removed.")
        }
    }

    fun insertWishlistItem(item: WishlistItem): Long {
        if (item.name.isBlank() || item.size == null || item.size !in 39..43) {
            Log.e("DatabaseHelper", "Invalid data: Item name cannot be empty and size must be between 39 and 43.")
            return -1L
        }

        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_ITEM_NAME, item.name)
                put(COLUMN_QUANTITY, item.quantity)
                put(COLUMN_DESCRIPTION, item.description)
                put(COLUMN_PRICE, item.price)
                put(COLUMN_SIZE, item.size)
                put(COLUMN_IMAGE_RES_ID, item.imageResId)
            }
            db.insertWithOnConflict(TABLE_WISHLIST, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting item: ${e.message}", e)
            -1L
        } finally {
            db.close()
        }
    }

    fun getWishlistItems(): List<WishlistItem> {
        val db = readableDatabase
        val wishlist = mutableListOf<WishlistItem>()
        val cursor = db.query(
            TABLE_WISHLIST,
            arrayOf(
                COLUMN_ITEM_NAME,
                COLUMN_QUANTITY,
                COLUMN_DESCRIPTION,
                COLUMN_PRICE,
                COLUMN_SIZE,
                COLUMN_IMAGE_RES_ID
            ),
            null, null, null, null, null
        )

        cursor.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                val quantity = it.getInt(it.getColumnIndexOrThrow(COLUMN_QUANTITY))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val price = it.getDouble(it.getColumnIndexOrThrow(COLUMN_PRICE))
                val size = it.getInt(it.getColumnIndexOrThrow(COLUMN_SIZE))
                val imageResId = it.getInt(it.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID))

                wishlist.add(
                    WishlistItem(
                        name = name,
                        quantity = quantity,
                        description = description,
                        price = price,
                        size = size,
                        imageResId = imageResId
                    )
                )
            }
        }
        return wishlist
    }

    fun updateWishlistItem(item: WishlistItem): Int {
        if (item.name.isBlank() || item.size == null || item.size !in 39..43) {
            Log.e("DatabaseHelper", "Invalid data: Item name cannot be empty and size must be between 39 and 43.")
            return -1
        }

        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_QUANTITY, item.quantity)
                put(COLUMN_DESCRIPTION, item.description)
                put(COLUMN_PRICE, item.price)
                put(COLUMN_IMAGE_RES_ID, item.imageResId)
            }
            db.update(
                TABLE_WISHLIST,
                values,
                "$COLUMN_ITEM_NAME = ? AND $COLUMN_SIZE = ?",
                arrayOf(item.name, item.size.toString())
            )
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error updating item: ${e.message}", e)
            -1
        } finally {
            db.close()
        }
    }

    fun deleteWishlistItem(itemName: String, size: Int?): Int {
        if (itemName.isBlank() || size == null || size !in 39..43) {
            Log.e("DatabaseHelper", "Invalid data: Item name cannot be empty and size must be between 39 and 43.")
            return -1
        }

        val db = writableDatabase
        return try {
            db.delete(
                TABLE_WISHLIST,
                "$COLUMN_ITEM_NAME = ? AND $COLUMN_SIZE = ?",
                arrayOf(itemName, size.toString())
            )
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error deleting item: ${e.message}", e)
            -1
        } finally {
            db.close()
        }
    }
}
