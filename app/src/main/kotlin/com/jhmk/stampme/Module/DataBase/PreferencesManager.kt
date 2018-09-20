@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.jhmk.stampme.Module.DataBase

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.jhmk.stampme.Model.ConstVariables

object PreferencesManager {
    val TAG = this.javaClass.simpleName

    fun saveUserId(context: Context, id: String) {
        Log.d(TAG, "##### saveUserId #####")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(ConstVariables.PREF_KEY_USER_ID, id)
        editor.apply()
    }

    fun saveUserPw(context: Context, pw: String) {
        Log.d(TAG, "##### saveUserPw #####")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(ConstVariables.PREF_KEY_USER_PW, pw)
        editor.apply()
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