package com.jhmk.stampme.Module.Register

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhmk.stampme.Model.ConstVariables
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_fragment_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.toast

class RegisterFragment : Fragment(), Register.view, View.OnClickListener {
    val TAG = this.javaClass.simpleName
    lateinit var mPresenter: RegisterPresenter
    var mUserType = -1;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateView @#####")
        mPresenter = RegisterPresenter(this)
        return inflater.inflate(R.layout.layout_fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated @#####")
        super.onActivityCreated(savedInstanceState)
        initializeUi()
    }

    fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
        txt_title_toolbar.text = resources.getString(R.string.string_title_create_account)
        img_back_toolbar.setOnClickListener(this)
        img_register_proceed.setOnClickListener(this)
        img_register_start.setOnClickListener(this)
        img_register_select_buyer.setOnClickListener(this)
        img_register_select_seller.setOnClickListener(this)
        img_register_select_seller.setBackgroundResource(R.drawable.btn_seller_n_2)
    }

    override fun onResultNextStep(flag: Boolean, msg: Int) {
        Log.d(TAG, "##### onResyultNextStep #####")
        activity?.toast(resources.getString(msg))
        if (flag) {
            txt_title_toolbar.text = resources.getString(R.string.string_title_set_up_profile)
            layout_register_create_account.visibility = View.GONE
            layout_register_setup_profile.visibility = View.VISIBLE
        }
    }

    override fun onResultCreateAccount(flag: Boolean, msg: Int) {
        Log.d(TAG, "##### onResultCreateAccount #####")
        if (flag) {
            mPresenter.requestFinishRegister(
                    User(
                            edtxt_register_id.text.toString()
                            , edtxt_register_pw.text.toString()
                            , edtxt_register_fullname.text.toString()
                            , edtxt_register_fullname.text.toString()
                            , edtxt_register_location.text.toString()
                            , mUserType))
        } else {
            activity?.toast(resources.getString(msg))
        }

    }

    override fun onClick(v: View) {
        Log.d(TAG, "##### onClick #####")
        when (v.id) {
            img_back_toolbar.id -> {
                mPresenter.requestBackButton(layout_register_create_account)
            }
            img_register_proceed.id -> {
                mPresenter.requestNextStep(edtxt_register_id.text.toString(), edtxt_register_pw.text.toString(), edtxt_register_confirm_pw.text.toString())
            }
            img_register_start.id -> {
                mPresenter.requestCreateAccount(edtxt_register_fullname.text.toString(), edtxt_register_phonenumber.text.toString(), edtxt_register_location.text.toString())
            }
            img_register_select_seller.id -> {
                mUserType = ConstVariables.USER_TYPE_SELLER
                img_register_select_seller.setBackgroundResource(R.drawable.btn_seller_n_2)
                img_register_select_buyer.setBackgroundResource(R.drawable.selector_buyer_btn)

            }
            img_register_select_buyer.id -> {
                mUserType = ConstVariables.USER_TYPE_BUYER
                img_register_select_buyer.setBackgroundResource(R.drawable.btn_buyer_p)
                img_register_select_seller.setBackgroundResource(R.drawable.selector_seller_btn)
            }
        }
    }
}