package com.jhmk.stampme.Module.MyInfo

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhmk.stampme.Model.Stamps
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_fragment_myinfo.*

class MyInfoFragment : Fragment(), MyInfo.view {
    val TAG = this.javaClass.simpleName
    private lateinit var mCurrentUser: User
    private lateinit var mPresenter: MyInfo.presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateView #####")
        mCurrentUser = arguments?.getSerializable("UserItem") as User
        mPresenter = MyInfoPresenter(this)
        return inflater.inflate(R.layout.layout_fragment_myinfo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated #####")
        super.onActivityCreated(savedInstanceState)
        initializeUi()
    }

    fun initializeUi() {
        Log.d(TAG, "##### initializeUi ##### userId : ${mCurrentUser.userId}")
        mPresenter.requestMakeBarcode(mCurrentUser)
        mPresenter.requestGetMyStamp(mCurrentUser)
    }

    override fun onResultMakeBarcode(user: User, bitmap: Bitmap?) {
        Log.d(TAG, "##### onResultMakeBarcode ##### user.id : ${user.userId}")
        if (bitmap != null) {
            img_info_barcode.setImageBitmap(bitmap)
        }
        txt_info_id.setText(user.userId)
        txt_info_name.setText(user.userName)
    }

    override fun onResultGetMyStamp(user: User, resultList: MutableList<Stamps?>?) {
        Log.d(TAG, "##### onResultGetMyStamp #####")
        if (resultList != null) {
            for ((index, value) in resultList.withIndex()) {
                Log.d(TAG, "stampSource : ${value?.stampSource} stampReason : ${value?.stampReason} ")
            }
        }
    }
}