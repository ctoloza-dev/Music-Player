package com.music_player.modules

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.music_player.R
import com.music_player.utils.Globals.Companion.NotificationItems.CHANNEL_ID
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

    @Singleton
    @Provides
    fun provideMediaSession(
        @ApplicationContext context: Context
    ): MediaSessionCompat = MediaSessionCompat(
        context,
        context.getString(R.string.app_name)
    )

    @Singleton
    @Provides
    fun provideNotification(
        @ApplicationContext context: Context,
        mediaSession: MediaSessionCompat
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID.getDesc())
            .setSmallIcon(R.drawable.ico_playlist)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.splash))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setVisibility(VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)

    }


}