package com.jhmk.stampme.Module.MyInfo

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhmk.stampme.Model.MyStamps
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.Adapter.StampsRecyclerviewAdapter
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_fragment_stampme.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class StampMeFragment : Fragment(), StampMe.view, StampsRecyclerviewAdapter.IClickListener {
    val TAG = this.javaClass.simpleName
    private lateinit var mCurrentUser: User
    private lateinit var mPresenter: StampMe.presenter
    private lateinit var mAdapter : StampsRecyclerviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateView #####")
        mCurrentUser = arguments?.getSerializable("UserItem") as User
        mPresenter = StampMePresenter(this)
        return inflater.inflate(R.layout.layout_fragment_stampme, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated #####")
        super.onActivityCreated(savedInstanceState)
        initializeUi()
    }

    fun initializeUi() {
        Log.d(TAG, "##### initializeUi ##### userId : ${mCurrentUser.userId}")
        txt_title_toolbar.text = resources.getString(R.string.string_title_my_stamps)
        mPresenter.requestMakeBarcode(mCurrentUser)
        mPresenter.requestGetMyStamp(mCurrentUser)
    }

    override fun onClick(id: Int) {
        Log.d(TAG, "##### onCLick #####")
    }

    override fun onResultMakeBarcode(user: User, bitmap: Bitmap?) {
        Log.d(TAG, "##### onResultMakeBarcode ##### user.id : ${user.userId}")
        if (bitmap != null) {
            img_info_barcode.setImageBitmap(bitmap)
        }
        txt_info_name.setText(user.userName)
    }

    override fun onResultGetMyStamp(resultList: MutableList<MyStamps?>?) {
        Log.d(TAG, "##### onResultGetMyStamp #####")
        if (resultList != null) {
            for(value in resultList.withIndex()){
                Log.d(TAG, "##### onResultGetMyStamp ##### ${value.value!!.stampSourceType}")
            }
            Log.d(TAG, "##### onResultGetMyStamp list size : ${resultList.size}#####")
            mAdapter = StampsRecyclerviewAdapter(activity as Context, resultList, this)
            list_latest_stamps.adapter = mAdapter
            list_latest_stamps.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            list_latest_stamps.setHasFixedSize(true)
        }
    }
}