package com.sanam.nizekinterview.data.cache.sharepreference

import android.content.Context
import android.content.SharedPreferences
import com.sanam.nizekinterview.data.cache.sharepreference.Preferences.Companion.PREFERENCES_NAME

abstract class AbstractSharedPreferences(val context: Context) :
    Preferences {
    protected val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

}