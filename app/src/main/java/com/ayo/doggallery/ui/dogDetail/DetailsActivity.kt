package com.ayo.doggallery.ui.dogDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayo.domain.model.DogDomain
import com.ayo.doggallery.R
import com.ayo.doggallery.databinding.ActivityDogDetailsBinding
import com.ayo.doggallery.utils.ImageLoaderUtils

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogDetailsBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        setUpView()
    }

    private fun setUpView() {
        val dog: DogDomain =
            intent?.extras?.getParcelable("dog") ?: throw IllegalStateException("dog is null")

        supportActionBar?.title = dog.name
        binding.apply {
            ImageLoaderUtils.loadImage(this@DetailsActivity, dog.referenceImageId, binding.dogImage)
            displayName.text = getString(R.string.name, dog.name)
            temperament.text = getString(R.string.dog_temperament, dog.temperament)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return true
    }
}
