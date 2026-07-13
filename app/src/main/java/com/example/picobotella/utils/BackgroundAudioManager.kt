package com.example.picobotella.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.picobotella.R

class BackgroundAudioManager(context: Context) {

    private val appContext = context.applicationContext
    private var mediaPlayer: MediaPlayer? = null

    fun start() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(appContext, R.raw.background_music).apply {
                isLooping = true
                setVolume(0.35f, 0.35f)
            }
        }
        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
