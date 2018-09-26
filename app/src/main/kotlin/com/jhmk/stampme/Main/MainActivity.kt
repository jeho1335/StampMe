package com.jhmk.stampme.Main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.jhmk.stampme.Model.ConstVariables
import com.jhmk.stampme.Model.EventBusObject
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.DataBase.PreferencesManager
import com.jhmk.stampme.Module.Home.HomeFragment
import com.jhmk.stampme.Module.Login.LoginFragment
import com.jhmk.stampme.Module.MyInfo.MyInfoFragment
import com.jhmk.stampme.Module.Register.RegisterFragment
import com.jhmk.stampme.Module.Settings.SettingsFragment
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), View.OnClickListener, Main.view {
    val TAG = this.javaClass.simpleName
    private lateinit var mPresenter: MainPresenter
    private lateinit var mContentView: View
    private lateinit var mCurrentUser: User

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
        img_main_tab_1.setOnClickListener(this)
        img_main_tab_2.setOnClickListener(this)
        img_main_tab_3.setOnClickListener(this)
        mContentView = (layout_main_content as View)
    }

    override fun onClick(v: View) {
        Log.d(TAG, "##### onClick #####")
        when (v.id) {

            img_main_tab_1.id, img_main_tab_2.id, img_main_tab_3.id -> {
                try {
                    handleFragment(v.id, mCurrentUser)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun onResultLogin(result: Boolean, msg: Int, user: User) {
        Log.d(TAG, "##### onResultLogin ##### isSuccess : $result")
        toast(resources.getString(msg))
        if (result) {
            mCurrentUser = user
            mPresenter.requestSaveUserInfo(this, user)
            if (user.userType == ConstVariables.USER_TYPE_BUYER) {
                handleFragment(img_main_tab_2.id, user)
            } else if (user.userType == ConstVariables.USER_TYPE_SELLER) {
            }
        } else {
            handleFragment(ConstVariables.SHOW_FRAGMENT_LOGIN, user)
        }
    }


    override fun onResultRegister(result: Boolean, msg: Int, user: User) {
        Log.d(TAG, "##### onResultRegister #####")
        toast(resources.getString(msg))
        if (result) {
            mPresenter.requestSaveUserInfo(this, user)
            if (user.userType == ConstVariables.USER_TYPE_BUYER) {
                handleFragment(img_main_tab_2.id, user)
            } else if (user.userType == ConstVariables.USER_TYPE_SELLER) {
            }
        } else {
            handleFragment(ConstVariables.SHOW_FRAGMENT_LOGIN, user)
        }
    }

    override fun onResultLogout(result: Boolean, msg: Int) {
        Log.d(TAG, "##### onResultLogout #####")
        toast(resources.getString(msg))
        if (result) {
            handleFragment(ConstVariables.SHOW_FRAGMENT_LOGIN, null)
        } else {

        }
    }

    fun loadLoginState() {
        Log.d(TAG, "##### loadLoginState #####")
        val loadedUser = PreferencesManager.loadUserInfo(this)
        if (loadedUser.userId == "") {
            handleFragment(ConstVariables.SHOW_FRAGMENT_LOGIN, null)
        } else {
            mPresenter.requestLogin(User(loadedUser.userId, loadedUser.userPw))
        }
    }

    fun handleFragment(state: Int, user: User?) {
        Log.d(TAG, "##### handleFragment #####")
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        var fr = Fragment()
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)

        when (state) {
            ConstVariables.SHOW_FRAGMENT_LOGIN -> {
                layout_main_tab.visibility = View.GONE
                layout_main_toolbar.visibility = View.GONE
                fr = LoginFragment()
            }
            ConstVariables.SHOW_FRAGMENT_REGISTER -> {
                layout_main_tab.visibility = View.GONE
                layout_main_toolbar.visibility = View.VISIBLE
                fr = RegisterFragment()
            }
            img_main_tab_1.id -> {
                layout_main_tab.visibility = View.VISIBLE
                layout_main_toolbar.visibility = View.VISIBLE
                fr = MyInfoFragment()
                if (user != null) {
                    val bundle = Bundle()
                    bundle.putSerializable("UserItem", user)
                    fr.arguments = bundle
                }
            }
            img_main_tab_2.id -> {
                layout_main_tab.visibility = View.VISIBLE
                layout_main_toolbar.visibility = View.VISIBLE
                fr = HomeFragment()
            }
            img_main_tab_3.id -> {
                layout_main_tab.visibility = View.VISIBLE
                layout_main_toolbar.visibility = View.VISIBLE
                fr = SettingsFragment()
                val bundle = Bundle()
                bundle.putSerializable("UserItem", user)
                fr.arguments = bundle
            }
        }

        if (fr.isAdded) {
            ft.show(fr)
        } else {
            ft.add(mContentView.id, fr, fr.javaClass.simpleName)
        }


        ft.commit()
    }

    @Subscribe
    fun onEvent(obj: EventBusObject) {
        Log.d(TAG, "##### onEVent ##### obj : ${obj.key}")
        when (obj.key) {
            ConstVariables.EVENTBUS_REQUELST_LOGIN -> mPresenter.requestLogin(obj.val1 as User)
            ConstVariables.EVENTBUS_REQUELST_REGISTER -> mPresenter.requestRegister(obj.val1 as User)
            ConstVariables.EVENTBUS_REQUEST_LOGOUT -> mPresenter.requestDeleteUserInfo(this, obj.val1 as User)
            ConstVariables.EVENTBUS_SHOW_REGISTER -> handleFragment(ConstVariables.SHOW_FRAGMENT_REGISTER, null)
            ConstVariables.EVENTBUS_CHANGE_TOOLBAR -> txt_title_main_toolbar.text = obj.val1 as String
        }
    }
}
