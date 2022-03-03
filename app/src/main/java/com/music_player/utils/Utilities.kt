package com.music_player.utils


/**
 * Created by David on 27-01-2022.
 */
interface Utilities {
    fun showToast(msg: Any, duration: Int)
    fun showError()
    fun showSuccess()
    fun showAlert(type: Int, title: String, message: String, optDialog: OptionDialog)
    fun showDialogPermission(msg: String)
}