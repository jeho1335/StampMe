package com.jhmk.stampme.Module.Settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_fragment_settings.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class SettingsFragment : Fragment(), View.OnClickListener {
    val TAG = this.javaClass.simpleName
    private var mPresenter = SettingsPresenter()
    private lateinit var mCurrentUser: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateView #####")
        mCurrentUser = arguments?.getSerializable("UserItem") as User
        return inflater.inflate(R.layout.layout_fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "##### onActivityCreated #####")
        initializeUi()
    }

    fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
        txt_settings_logout.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        Log.d(TAG, "##### onClick #####")
        when (p0.id) {
            txt_settings_logout.id -> {
                activity!!.alert(resources.getString(R.string.alert_logout_title)){
                    yesButton {  mPresenter.requestLogout(mCurrentUser) }
                    noButton {  }
                }.show()

            }
        }
    }
}