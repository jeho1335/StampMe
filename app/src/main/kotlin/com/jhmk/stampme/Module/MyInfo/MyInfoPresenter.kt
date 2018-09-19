package com.jhmk.stampme.Module.MyInfo

import android.util.Log
import com.google.gson.Gson
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.Utils.MakeBarcode

class MyInfoPresenter(view : MyInfo.view) : MyInfo.presenter {
    val TAG = this.javaClass.simpleName
    val view = view

    override fun requestMakeBarcode(user: User) {
        Log.d(TAG, "##### requestMakeBarcode #####")
        var gson = Gson()
        var jsonObject = gson.toJson(user)
        view.onResultMakeBarcode(user, MakeBarcode.getQRCode(jsonObject))
    }
}