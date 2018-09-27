package com.jhmk.stampme.Module.MyInfo

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.jhmk.stampme.Model.MyStamps
import com.jhmk.stampme.Model.Shops
import com.jhmk.stampme.Model.Stamps
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.DataBase.DataBaseReference
import com.jhmk.stampme.Module.Utils.MakeBarcode
import kotlin.collections.ArrayList

class StampMePresenter(view: StampMe.view) : StampMe.presenter {
    val TAG = this.javaClass.simpleName
    private val mView = view

    override fun requestMakeBarcode(user: User) {
        Log.d(TAG, "##### requestMakeBarcode #####")
        val gson = Gson()
        val jsonObject = gson.toJson(user)
        mView.onResultMakeBarcode(user, MakeBarcode.getQRCode(jsonObject))
    }

    override fun requestGetMyStamp(user: User) {
        Log.d(TAG, "##### requestGetMyStamp userId : ${user.userId}#####")
        val resultList: MutableList<Stamps?> = ArrayList()
        DataBaseReference.mStampsDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### requestGetMyStamp ##### onCancelled")
                DataBaseReference.mStampsDatabaseReference.removeEventListener(this)
                mView.onResultGetMyStamp(null)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### requestGetMyStamp ##### onDataChange")
                val child = dataSnapShot.child(user.userId).children.toMutableList()

                for (value in child.withIndex()) {
                    resultList.add(value.value.getValue(Stamps().javaClass))
                }

                getCurrentShopList(resultList)
                DataBaseReference.mStampsDatabaseReference.removeEventListener(this)
            }
        })
    }

    private fun getCurrentShopList(stampList: MutableList<Stamps?>): MutableList<Shops?> {
        val shopList: MutableList<Shops?> = ArrayList()
        val myStampResultList: MutableList<MyStamps?> = ArrayList()
        DataBaseReference.mShopsDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### getCurrentShopList ##### onCancelled")
                DataBaseReference.mShopsDatabaseReference.removeEventListener(this)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### getCurrentShopList #####")
                val child = dataSnapShot.children.toMutableList()

                for (value in child.withIndex()) {
                    shopList.add(value.value.getValue(Shops().javaClass))
                }

                Log.d(TAG, "##### getCurrentShopList ##### resultList.size :  ${shopList.size} stampList.size : ${stampList.size}")

                for((index, value) in stampList.withIndex()){
                    Log.d(TAG, "##### getCurrentShopList ##### ${value?.stampSource}")
                    for(value in shopList.withIndex()){
                        if( value.value?.shopName == stampList[index]?.stampSource){
                            myStampResultList.add(MyStamps(stampList[index]!!.stampSource, value.value!!.shopType, value.value!!.shopImageUrl, stampList[index]!!.stampReason, shopList[index]!!.shopAddress))
                            Log.d(TAG, "##### getCurrentShopList ##### ${stampList[index]!!.stampSource}   ${shopList[index]!!.shopImageUrl} ")
                        }
                    }

                }
                mView.onResultGetMyStamp(myStampResultList)

                DataBaseReference.mShopsDatabaseReference.removeEventListener(this)
            }
        })
        return shopList
    }
}