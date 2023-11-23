package com.ravinada.riomoneyassignment.ui.home

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ravinada.riomoneyassignment.R
import com.ravinada.riomoneyassignment.data.api.response.Product
import com.ravinada.riomoneyassignment.databinding.ItemProductBinding
import com.ravinada.riomoneyassignment.ui.utils.loadUrl

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var list: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataModel: Product) {
            with(binding) {
                imageViewProduct.loadUrl(
                    url = dataModel.imageUrl,
                    placeholderResId = R.drawable.splash_background,
                    errorResId = R.drawable.splash_background
                )
                textViewRating.text = dataModel.rating
                textViewBrandName.text = dataModel.brand
                textViewName.text = dataModel.name
                textViewPrice.text =
                    itemView.resources.getString(R.string.price_format, dataModel.price.toString())
                textViewStrikePrice.text = itemView.resources.getString(
                    R.string.price_format,
                    dataModel.strikePrice.toString()
                )
                textViewStrikePrice.paintFlags =
                    textViewStrikePrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                root.setOnClickListener {
                    // TODO: add item click listener
                }
            }
        }
    }
}
