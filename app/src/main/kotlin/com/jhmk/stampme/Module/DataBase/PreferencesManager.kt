package com.jhmk.stampme.Module.DataBase

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.jhmk.stampme.Module.Utils.ConstVariables

object PreferencesManager {
    val TAG = this.javaClass.simpleName

    fun saveUserId(context: Context, id: String) {
        Log.d(TAG, "##### saveUserId #####")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(ConstVariables.PREF_KEY_USER_ID, id)
        editor.commit()
    }

    fun saveUserPw(context: Context, pw: String) {
        Log.d(TAG, "##### saveUserPw #####")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(ConstVariables.PREF_KEY_USER_PW, pw)
        editor.commit()
    }

    fun loadUserId(context: Context): String {
        Log.d(TAG, "##### loadUserId #####")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(ConstVariables.PREF_KEY_USER_ID, "")
    }

    fun loadUserPw(context: Context): String {
        Log.d(TAG, "##### loadUserPW #####")
        val sharedPreferenceManager = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferenceManager.getString(ConstVariables.PREF_KEY_USER_PW, "")
    }
}