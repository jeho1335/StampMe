package com.jhmk.stampme.Main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import com.jhmk.stampme.Module.DataBase.PreferencesManager
import com.jhmk.stampme.Module.Login.LoginFragment
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), View.OnClickListener, Main.view {
    val TAG = this.javaClass.simpleName
    lateinit var mPresenter: MainPresenter
    lateinit var mContentView: View
    lateinit var mLoginFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onCreate #####")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)
        initializeUI()
        loadLoginState()
    }

    override fun onClick(v: View) {
        Log.d(TAG, "##### onClick #####")
        when (v.id) {
            img_drawer_icon.id -> {
                if (layout_drawer.isDrawerOpen(Gravity.LEFT)) {
                    layout_drawer.closeDrawer(Gravity.LEFT)
                } else {
                    layout_drawer.openDrawer(Gravity.LEFT)
                }
            }
        }
    }

    override fun onResultLogin(result: Boolean, msg: String) {
        Log.d(TAG, "##### onResultLogin #####")
        toast(msg)
        handleFragment(1)
        if (result) {

        } else {

        }
    }

    override fun onResultRegister(result: Boolean, msg: String) {
        handleFragment(1)
    }

    fun initializeUI() {
        Log.d(TAG, "##### initializeUI #####")
        img_drawer_icon.setOnClickListener(this)
        mContentView = (layout_content as View)
        mLoginFragment = LoginFragment()
    }

    fun loadLoginState() {
        Log.d(TAG, "##### loadLoginState #####")
        val currentUserId = PreferencesManager.loadUserId(this)
        val currentUserPw = PreferencesManager.loadUserPw(this)
        if (currentUserId == "") {
            mPresenter.requestLogin(currentUserId, currentUserPw)
        } else {

        }
    }

    fun handleFragment(state: Int) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mContentView.id, mLoginFragment, mLoginFragment.javaClass.simpleName)
        ft.commit()
        Log.d(TAG, "##### handleFragment #####")

    }
}
