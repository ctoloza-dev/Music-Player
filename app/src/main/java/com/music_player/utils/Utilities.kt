package com.music_player.utils


interface Utilities {
    fun showToast(msg: Any, duration: Int)
    fun showError()
    fun showSuccess()
    fun showAlert(type: Int, title: String, message: String, optDialog: OptionDialog)
}