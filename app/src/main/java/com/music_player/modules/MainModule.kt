package com.music_player.modules

import android.content.Context
import android.media.MediaPlayer
import com.music_player.utils.Utilities
import com.music_player.utils.UtilitiesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by David on 02-03-2022.
 */
@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideUtils(): Utilities = UtilitiesImpl()

    @Singleton
    @Provides
    fun provideMediaPlayer(): MediaPlayer = MediaPlayer()


}