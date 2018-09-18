package com.jhmk.stampme.Main

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jhmk.stampme.Module.DataBase.DataBaseReference
import com.jhmk.stampme.R

class MainPresenter(view: Main.view) : Main.presenter {
    val TAG = this.javaClass.simpleName
    val mView = view

    override fun requestLogin(id: String, pw: String) {
        DataBaseReference.mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                DataBaseReference.mDatabaseReference.removeEventListener(this)
                mView.onResultLogin(false, R.string.toast_login_failed.toString())
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                var child = dataSnapShot.children.iterator()

                while (child.hasNext()) {
                    if (child.next().key.equals(id)) {
                        mView.onResultLogin(true, R.string.toast_login_success.toString())
                        DataBaseReference.mDatabaseReference.removeEventListener(this)
                        return
                    }
                }
                mView.onResultLogin(false, R.string.toast_login_failed.toString())
                DataBaseReference.mDatabaseReference.removeEventListener(this)
            }
        })
    }

    override fun requestRegister(id: String, pw: String) {
        DataBaseReference.mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                DataBaseReference.mDatabaseReference.removeEventListener(this)
                mView.onResultRegister(false, R.string.toast_register_failed.toString())
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                var child = dataSnapShot.children.iterator()

                while (child.hasNext()) {
                    if (child.next().key.equals(id)) {
                        mView.onResultRegister(false, R.string.toast_register_failed.toString())
                        DataBaseReference.mDatabaseReference.removeEventListener(this)
                        return
                    }
                }
                DataBaseReference.mDatabaseReference.child(id).child("userPw").setValue(pw)
                DataBaseReference.mDatabaseReference.child(id).child("userStamp").setValue(0)
                mView.onResultRegister(false, R.string.toast_register_success.toString())
                DataBaseReference.mDatabaseReference.removeEventListener(this)
            }
        })
    }
}