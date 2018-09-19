package com.jhmk.stampme.Module.MyInfo

import android.graphics.Bitmap
import com.jhmk.stampme.Model.User

interface MyInfo {
    interface view{
        fun onResultMakeBarcode(user : User, bitmap : Bitmap?)
    }
    interface presenter{
        fun requestMakeBarcode(user : User)
    }
}