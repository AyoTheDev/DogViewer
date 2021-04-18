package com.ayo.doggallery.ui.dogList

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ayo.doggallery.databinding.ActivityDogListBinding
import com.ayo.doggallery.di.ViewModelFactory
import com.ayo.doggallery.ui.dogDetail.DetailsActivity
import com.ayo.doggallery.ui.listeners.SearchQueryListener
import com.ayo.doggallery.ui.viewmodel.MainViewModel
import com.ayo.doggallery.ui.extensions.showToast
import com.ayo.doggallery.utils.Resource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DogListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityDogListBinding

    private val dogListAdapter: DogListAdapter by lazy {
        DogListAdapter(dogListener)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        setUpListeners()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.dogsLiveData.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    dogListAdapter.update(resource.data)
                    isLoading(false)
                }
                is Resource.Loading -> isLoading(resource.loading)
                is Resource.Failure -> {
                    isLoading(false)
                    showToast(resource.msg)
                }
            }

        })
    }


    private val dogListener = object : DogListAdapter.Listener {
        override fun onClick(position: Int, sharedElementView: ImageView) {
            val dog = dogListAdapter.getItem(position)
            val intent = Intent(this@DogListActivity, DetailsActivity::class.java)
            intent.putExtra("dog", dog)
            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    this@DogListActivity,
                    sharedElementView,
                    "dog"
                )

            startActivity(intent, options.toBundle())

        }
    }

    private fun setUpListeners() {
        binding.searchBox.addTextChangedListener(searchQueryListener)
        binding.searchButton.setOnClickListener {
            val queryText = binding.searchBox.text.toString()
            searchQueryListener.onSearchButtonClicked(queryText)
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.loading.visibility =
            if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private val searchQueryListener =
        SearchQueryListener(this.lifecycle,
            { queryText ->
                queryText.let {
                    if (it.isNotBlank()) viewModel.searchDogs(it)
                }
            },
            { viewModel.getDogsList() })


    private fun setUpView() {
        binding.dogList.apply {
            adapter = dogListAdapter
            layoutManager = GridLayoutManager(this@DogListActivity, 3)
        }
    }
}
