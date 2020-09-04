package com.example.outdoor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.outdoor.R
import com.example.outdoor.models.RentalListData

class RentalListAdapter(
) :
    RecyclerView.Adapter<RentalListAdapter.ViewHolder>() {

    private lateinit var rentals: List<RentalListData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rental_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rental = rentals[position]
        Glide.with(holder.image.context).load(rental.imageUrl).into(holder.image)
        holder.name.text = rental.name
    }

    override fun getItemCount(): Int {
        return rentals.size
    }

    fun setRentals(rentalInfo: List<RentalListData>) {
        rentals = rentalInfo
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var name: TextView

        init {
            with(itemView) {
                image = findViewById(R.id.rentalImage)
                name = findViewById(R.id.rentalName)
            }
        }
    }
}