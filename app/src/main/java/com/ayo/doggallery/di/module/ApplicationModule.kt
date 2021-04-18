package com.ayo.doggallery.di.module

import android.content.Context
import com.ayo.doggallery.App
import com.ayo.doggallery.common.CoroutineContextProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: App){

    @Provides
    fun provideContext(): Context = app.applicationContext

    @Provides
    fun provideCoroutineContext(): CoroutineContextProvider {
        return CoroutineContextProvider()
    }
}