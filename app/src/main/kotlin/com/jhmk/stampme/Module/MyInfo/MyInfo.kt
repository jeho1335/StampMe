package com.jhmk.stampme.Module.MyInfo

import android.graphics.Bitmap
import com.jhmk.stampme.Model.Stamps
import com.jhmk.stampme.Model.User

interface MyInfo {
    interface view{
        fun onResultMakeBarcode(user : User, bitmap : Bitmap?)
        fun onResultGetMyStamp(user : User, resultList : MutableList<Stamps?>?)
    }
    interface presenter{
        fun requestMakeBarcode(user : User)
        fun requestGetMyStamp(user : User)
    }
}