package com.jhmk.stampme.Module.MyInfo

import android.graphics.Bitmap
import com.jhmk.stampme.Model.MyStamps
import com.jhmk.stampme.Model.Stamps
import com.jhmk.stampme.Model.User

interface StampMe {
    interface view{
        fun onResultMakeBarcode(user : User, bitmap : Bitmap?)
        fun onResultGetMyStamp(resultList : MutableList<MyStamps?>?)
        fun onResultSeperateMyStamp(cafeList : MutableList<MyStamps?>, restrauntList : MutableList<MyStamps?>, storeList : MutableList<MyStamps?>, martList : MutableList<MyStamps?>, publicList : MutableList<MyStamps?>, etcList : MutableList<MyStamps?>)
    }
    interface presenter{
        fun requestMakeBarcode(user : User)
        fun requestGetMyStamp(user : User)
        fun requestSeperateMyStamp(myStampList : MutableList<MyStamps?>)
    }
}