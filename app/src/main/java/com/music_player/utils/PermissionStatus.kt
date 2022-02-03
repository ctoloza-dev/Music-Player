package com.music_player.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import cn.pedant.SweetAlert.SweetAlertDialog
import com.music_player.Globals.Companion.permits
import com.music_player.R

open class PermissionStatus(var context: Context) {
    private var activity: Activity = context as Activity
    private var utils = UtilitiesImpl(context)


    fun reqPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permits, 100)
        }
    }

    open fun validatePermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (idPermission in permits) {
                if (activity.checkSelfPermission(idPermission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Valida si el mensaje de permisos se marco como No volver a preguntar
     * return true si no se marca
     * return false si se marca
     */
    private fun validatePermissionsMsg(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (idPermission in permits) {
                if (!activity.shouldShowRequestPermissionRationale(idPermission)) {
                    return false
                }
            }
        }
        return true
    }

    private fun showDialogPermission(msg: String, selectMsg: Boolean) {
        utils.showAlert(SweetAlertDialog.ERROR_TYPE,
            context.getString(R.string.perm_Disable),
            msg,
            object : OptionDialog {
                override fun accept() {
                    if (selectMsg) {
                        reqPermissions()
                    } else {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:" + context.packageName)
                        context.startActivity(intent)
                    }
                }
            })
    }

    fun confirmPermissionMsg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!getMsgPermissions(context)) {
                setMsgPermissions(context, true)
                reqPermissions()
            } else if (!validatePermissionsMsg() && !validatePermissions()) {
                showDialogPermission(context.getString(R.string.perm_Disabled), false)
            } else if (validatePermissionsMsg() && !validatePermissions()) {
                showDialogPermission(context.getString(R.string.perm_accept), true)
            }
        }
    }

    companion object {
        fun getMsgPermissions(context: Context): Boolean {
            val preferences = context.getSharedPreferences("permissions", Context.MODE_PRIVATE)
            return preferences.getBoolean("msg", false)
        }

        fun setMsgPermissions(context: Context, msgPermission: Boolean) {
            val preferences = context.getSharedPreferences("permissions", Context.MODE_PRIVATE)
            val edit = preferences.edit()
            edit.putBoolean("msg", msgPermission)
            edit.apply()
        }
    }

}