package com.ayo.doggallery.ui.dogList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayo.domain.model.DogDomain
import com.ayo.doggallery.R

class DogListAdapter(private val listener: Listener) : RecyclerView.Adapter<DogItemViewHolder>() {

    private var items = mutableListOf<DogDomain>()

    //notifyDataSetChanged is an expensive operation, should use a diff call back
    fun update(list: List<DogDomain>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.dog_item, parent, false)
        return DogItemViewHolder(view, listener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DogItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    interface Listener {
        fun onClick(position: Int,sharedElementView:ImageView)
    }
}
