package com.ayo.doggallery.ui.dogList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ayo.domain.model.DogDomain
import com.ayo.doggallery.utils.ImageLoaderUtils
import kotlinx.android.synthetic.main.dog_item.view.*


class DogItemViewHolder(itemView: View, private val listener: DogListAdapter.Listener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    fun bind(dog: DogDomain) {
        val context = itemView.context
        itemView.apply {
            setOnClickListener(this@DogItemViewHolder)
            ImageLoaderUtils.loadImage(context, dog.referenceImageId, img)
            name.text = dog.name
        }
    }

    override fun onClick(v: View?) {
        listener.onClick(layoutPosition,itemView.img)
    }
}
