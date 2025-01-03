package com.example.fp_bp2.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.fp_bp2.DatabaseHelper
import com.example.fp_bp2.R
import com.example.fp_bp2.models.WishlistItem

class WishlistAdapter(
    private val items: MutableList<WishlistItem>,
    private val onSizeSelected: (WishlistItem, Int?) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val spinnerSize: Spinner = itemView.findViewById(R.id.spinner_size)
        val btnIncreaseQuantity: ImageButton = itemView.findViewById(R.id.btn_add)
        val btnDecreaseQuantity: ImageButton = itemView.findViewById(R.id.btn_min)
        val itemQuantity: TextView = itemView.findViewById(R.id.item_quantity)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = items[position]

        holder.itemName.text = item.name
        holder.itemPrice.text = item.getFormattedPrice()

        holder.itemQuantity.text = item.quantity.toString()

        val sizeOptions = listOf(null, 39, 40, 41, 42, 43)
        val context = holder.spinnerSize.context
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            sizeOptions.map { it?.toString() ?: "Sesuaikan Ukuran" }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinnerSize.adapter = adapter

        val selectedPosition = sizeOptions.indexOf(item.size)
        holder.spinnerSize.setSelection(selectedPosition.coerceAtLeast(0))

        holder.spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSize = sizeOptions[position]
                if (item.size != selectedSize) {
                    item.size = selectedSize
                    onSizeSelected(item, selectedSize)

                    val updateResult = DatabaseHelper(context).updateWishlistItem(item)
                    if (updateResult > 0) {
                        Toast.makeText(context, "Ukuran ${item.name} berhasil diubah ke $selectedSize.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Gagal mengubah ukuran ${item.name}.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        holder.btnIncreaseQuantity.setOnClickListener {
            item.quantity += 1
            holder.itemQuantity.text = item.quantity.toString()
            DatabaseHelper(holder.itemView.context).updateWishlistItem(item)
        }

        holder.btnDecreaseQuantity.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity -= 1
                holder.itemQuantity.text = item.quantity.toString()
                DatabaseHelper(holder.itemView.context).updateWishlistItem(item)
            } else {
                Toast.makeText(holder.itemView.context, "Quantity tidak boleh kurang dari 1", Toast.LENGTH_SHORT).show()
            }
        }

        holder.btnDelete.setOnClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin menghapus ${item.name} dari wishlist?")
                .setPositiveButton("Ya") { _, _ ->
                    val deleteResult = DatabaseHelper(context).deleteWishlistItem(item.name, item.size)
                    if (deleteResult > 0) {
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        Toast.makeText(context, "${item.name} berhasil dihapus.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Gagal menghapus ${item.name}.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    override fun getItemCount(): Int = items.size
}
