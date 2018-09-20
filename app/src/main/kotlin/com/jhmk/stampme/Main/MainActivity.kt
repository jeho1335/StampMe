package com.jhmk.stampme.Main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.DataBase.PreferencesManager
import com.jhmk.stampme.Module.Login.LoginFragment
import com.jhmk.stampme.Module.MyInfo.MyInfoFragment
import com.jhmk.stampme.Model.ConstVariables
import com.jhmk.stampme.Model.EventBusObject
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), View.OnClickListener, Main.view {
    val TAG = this.javaClass.simpleName
    lateinit var mPresenter: MainPresenter
    lateinit var mContentView: View
    lateinit var mLoginFragment: Fragment
    lateinit var mMyinfoFragment : Fragment
    lateinit var mCurrentUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onCreate #####")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        mPresenter = MainPresenter(this)
        initializeUI()
        loadLoginState()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "##### onDestroy #####")
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    fun initializeUI() {
        Log.d(TAG, "##### initializeUI #####")
        img_drawer_icon.setOnClickListener(this)
        mContentView = (layout_content as View)
        mLoginFragment = LoginFragment()
        mMyinfoFragment = MyInfoFragment()
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

    override fun onResultLogin(result: Boolean, msg: Int, user : User) {
        Log.d(TAG, "##### onResultLogin #####")
        toast(resources.getString(msg))
        if (result) {
            mPresenter.saveUserId(this, user.userId)
            mPresenter.saveUserPassword(this, user.userPw)
            if(user.userType == ConstVariables.USER_TYPE_GENERAL){
                mCurrentUser = user
                handleFragment(ConstVariables.SHOW_FRAGMENT_MYINFO)
            }else if(user.userType == ConstVariables.USER_TYPE_MANAGER) {
            }
        } else {
            handleFragment(ConstVariables.SHOW_FRAGMENT_LOGIN)
        }
    }


    override fun onResultRegister(result: Boolean, msg: Int) {
        toast(resources.getString(msg))
    }


    fun loadLoginState() {
        Log.d(TAG, "##### loadLoginState #####")
        val currentUserId = PreferencesManager.loadUserId(this)
        val currentUserPw = PreferencesManager.loadUserPw(this)
        if (currentUserId == "") {
            handleFragment(ConstVariables.SHOW_FRAGMENT_LOGIN)
        } else {
            mPresenter.requestLogin(User(currentUserId, currentUserPw, "", "", ConstVariables.USER_TYPE_GENERAL))
        }
    }

    fun handleFragment(state: Int) {
        Log.d(TAG, "##### handleFragment #####")
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        when (state) {
            ConstVariables.SHOW_FRAGMENT_LOGIN -> {
                if(mLoginFragment.isAdded){
                    ft.show(mLoginFragment)
                }else {
                    ft.add(mContentView.id, mLoginFragment, mLoginFragment.javaClass.simpleName)
                }
            }
            ConstVariables.SHOW_FRAGMENT_MYINFO -> {
                val bundle = Bundle()
                bundle.putSerializable("UserItem", mCurrentUser)
                mMyinfoFragment.arguments = bundle

                if(mMyinfoFragment.isAdded){
                    ft.show(mMyinfoFragment)
                }else{
                    ft.add(mContentView.id, mMyinfoFragment, mMyinfoFragment.javaClass.simpleName)
                }

            }
        }
        ft.commit()
    }

    @Subscribe
    fun onEvent(obj: EventBusObject) {
        Log.d(TAG, "##### onEVent ##### obj : ${obj.key}")
        when(obj.key){
            ConstVariables.EVENTBUS_REQUELST_LOGIN -> mPresenter.requestLogin(obj.val1 as User)
            ConstVariables.EVENTBUS_REQUELST_REGISTER -> mPresenter.requestRegister(obj.val1 as User)
        }
    }
}
