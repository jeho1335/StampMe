package com.jhmk.stampme.Module.Home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhmk.stampme.Model.Shops
import com.jhmk.stampme.Module.Adapter.ShopsRecyclerviewAdapter
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_fragment_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class HomeFragment : Fragment(), Home.view, ShopsRecyclerviewAdapter.IClickListener {
    val TAG = this.javaClass.simpleName
    lateinit var mPresenter: HomePresenter
    lateinit var mAdapter: ShopsRecyclerviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateVIew #####")
        mPresenter = HomePresenter(this)
        return inflater.inflate(R.layout.layout_fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated #####")
        super.onActivityCreated(savedInstanceState)
        initializeUi()
    }

    fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
        txt_title_toolbar.text = resources.getString(R.string.string_title_nearby_shops)
        mPresenter.requestAroundShopList(activity as Context)
    }

    override fun onClick(pos: Int) {
        mAdapter.refreshItem(pos)
    }

    override fun onGetStampClick(id: Int) {
        //nothing
    }

    override fun onResultAroundShopList(list: MutableList<Shops?>) {
        Log.d(TAG, "##### onResultAroundShopList #####")
        mAdapter =

                ShopsRecyclerviewAdapter(activity as Context, list, this)
        list_home_shops.adapter = mAdapter
        list_home_shops.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        list_home_shops.setHasFixedSize(true)
        for ((index, value) in list.withIndex()) {
            Log.d(TAG, "targetBehavior : ${value?.shopTargetBehavior} + targetImageUrl : ${value?.shopImageUrl}")
        }
    }
}
