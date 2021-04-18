package com.ayo.doggallery.di.builder

import com.ayo.doggallery.ui.dogList.DogListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {
    @ContributesAndroidInjector
    fun contributeActivity(): DogListActivity
}
