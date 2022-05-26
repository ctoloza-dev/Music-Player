package com.music_player.utils

import android.content.Context
import android.os.Build
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.music_player.utils.Globals.Companion.permits
import com.music_player.R
import com.music_player.utils.logs.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by David on 28-01-2022.
 */
open class PermissionStatus @Inject constructor(@ApplicationContext val context: Context) {

    open fun checkPermissions(response: ResponseListener) {
        Dexter.withContext(context)
            .withPermissions(permits.toMutableList())
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.isAnyPermissionPermanentlyDenied) {
                        response.onResponse(context.getString(R.string.perm_Disabled))
                    }
                    if (report.areAllPermissionsGranted()) {
                        response.onResponse(context.getString(R.string.perm_granted))
                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    permissions!!.forEach {
                        Logger.error(it!!.name)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!getMsgPermissions(context)) {
                            setMsgPermissions(context, true)
                            token?.continuePermissionRequest()
                        } else {
                            response.onResponse(context.getString(R.string.perm_Disabled))
                        }
                    }
                }
            })
            .check()
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