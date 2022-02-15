package com.music_player.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.music_player.R
import com.music_player.utils.logs.Logger

interface OptionDialog {
    fun accept()
}

class UtilitiesImpl(private var ctx: Context) : Utilities {

    override fun showToast(msg: Any, duration: Int) {
        var message = "No Info Found"
        if (msg is String) message = msg else if (msg is Int) message = ctx.getString(msg)
        Toast.makeText(ctx, message, duration).show()
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

    override fun showSuccess() {
        TODO("Not yet implemented")
    }

    override fun showAlert(type: Int, title: String, message: String, optDialog: OptionDialog) {
        Logger.error("showCustomDialog: message: $message")
        val dialog = SweetAlertDialog(ctx, type)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmButtonBackgroundColor(ctx.resources.getColor(R.color.colorPrimary))
            .setConfirmButton(
                ctx.getString(R.string.accept)
            ) { sDialog ->
                sDialog.dismissWithAnimation()
                optDialog.accept()
            }
        dialog.show()
    }

    override fun showDialogPermission(msg: String) {
        showAlert(SweetAlertDialog.ERROR_TYPE,
            ctx.getString(R.string.perm_Disable),
            msg,
            object : OptionDialog {
                override fun accept() {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + ctx.packageName)
                    ctx.startActivity(intent)
                }
            })
    }
}