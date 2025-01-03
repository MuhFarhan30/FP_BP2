package com.example.fp_bp2

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.fp_bp2.models.WishlistItem

class UserRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun addWishlistItem(item: WishlistItem) {
        dbHelper.insertWishlistItem(item)
    }

    fun updateWishlistItem(item: WishlistItem) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_QUANTITY, item.quantity)
            put(DatabaseHelper.COLUMN_DESCRIPTION, item.description)
            put(DatabaseHelper.COLUMN_PRICE, item.price)
            put(DatabaseHelper.COLUMN_SIZE, item.size)
            put(DatabaseHelper.COLUMN_IMAGE_RES_ID, item.imageResId)
        }

        try {
            db.update(
                DatabaseHelper.TABLE_WISHLIST,
                values,
                "${DatabaseHelper.COLUMN_ITEM_NAME} = ? AND ${DatabaseHelper.COLUMN_SIZE} = ?",
                arrayOf(item.name, item.size.toString())
            )
        } catch (e: Exception) {
            Log.e("UserRepository", "Error in updateWishlistItem", e)
        } finally {
            db.close()
        }
    }

    fun deleteWishlistItem(itemName: String, size: Int) {
        val db = dbHelper.writableDatabase

        try {
            db.delete(
                DatabaseHelper.TABLE_WISHLIST,
                "${DatabaseHelper.COLUMN_ITEM_NAME} = ? AND ${DatabaseHelper.COLUMN_SIZE} = ?",
                arrayOf(itemName, size.toString())
            )
        } catch (e: Exception) {
            Log.e("UserRepository", "Error in deleteWishlistItem", e)
        } finally {
            db.close()
        }
    }

    fun getWishlistItems(): List<WishlistItem> {
        return dbHelper.getWishlistItems()
    }
}
