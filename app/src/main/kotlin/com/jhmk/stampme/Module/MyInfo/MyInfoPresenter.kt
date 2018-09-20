package com.jhmk.stampme.Module.MyInfo

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.jhmk.stampme.Model.Stamps
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.DataBase.DataBaseReference
import com.jhmk.stampme.Module.Utils.MakeBarcode
import java.util.*

class MyInfoPresenter(view: MyInfo.view) : MyInfo.presenter {
    val TAG = this.javaClass.simpleName
    val mView = view

    override fun requestMakeBarcode(user: User) {
        Log.d(TAG, "##### requestMakeBarcode #####")
        var gson = Gson()
        var jsonObject = gson.toJson(user)
        mView.onResultMakeBarcode(user, MakeBarcode.getQRCode(jsonObject))
    }

    override fun requestGetMyStamp(user: User) {
        Log.d(TAG, "##### requestGetMyStamp userId : ${user.userId}#####")
        val resultList: MutableList<Stamps?> = ArrayList()
        DataBaseReference.mStampsDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### requestGetMyStamp ##### onCancelled")
                DataBaseReference.mStampsDatabaseReference.removeEventListener(this)
                mView.onResultGetMyStamp(user, resultList)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### requestGetMyStamp ##### onDataChange")
                val child = dataSnapShot.child(user.userId).children.toMutableList()

                for(value in child.withIndex()){
                    resultList.add(value.value.getValue(Stamps().javaClass))
                }
                mView.onResultGetMyStamp(user, resultList)
                DataBaseReference.mStampsDatabaseReference.removeEventListener(this)
            }
        })
    }
}