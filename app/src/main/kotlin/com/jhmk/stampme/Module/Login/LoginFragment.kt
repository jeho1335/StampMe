package com.jhmk.stampme.Module.Login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jhmk.stampme.Module.DataBase.DataBaseReference
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_fragment_login.*
import org.jetbrains.anko.toast

class LoginFragment : Fragment(), View.OnClickListener {
    val TAG = this.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateView @@@@@")
        return inflater.inflate(R.layout.layout_fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "##### onActivityCreated #####")
        initializeUI()
    }

    override fun onClick(v: View) {
        Log.d(TAG, "##### onClick #####")
        when(v.id){
            test_login.id -> Log.d(TAG, "###")
            test_register.id -> DataBaseReference.mDatabaseReference.addListenerForSingleValueEvent(mRegisterListener)
        }
    }

    fun initializeUI() {
        Log.d(TAG, "##### initializeUI #####")
        test_login.setOnClickListener(this)
        test_register.setOnClickListener(this)
    }

    private fun registerAccount(id : String, pw : String){
        DataBaseReference.mDatabaseReference.child(id).child("userPw").setValue(pw)
        DataBaseReference.mDatabaseReference.child(id).child("userStamp").setValue(0)
    }

    var mRegisterListener : ValueEventListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError?) {
            activity?.toast(resources.getString(R.string.toast_register_failed))
            DataBaseReference.mDatabaseReference.removeEventListener(this)
        }

        override fun onDataChange(dataSnapShot: DataSnapshot) {
            var child = dataSnapShot.children.iterator()

            while(child.hasNext()){
                if(child.next().key.equals(test_id.text.toString())){
                    activity?.toast(resources.getString(R.string.toast_register_failed))
                    DataBaseReference.mDatabaseReference.removeEventListener(this)
                    return
                }
            }
            activity?.toast(resources.getString(R.string.toast_register_success))
            registerAccount(test_id.text.toString(), test_pw.text.toString())
            DataBaseReference.mDatabaseReference.removeEventListener(this)
        }
    }
}