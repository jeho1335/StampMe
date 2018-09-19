package com.jhmk.stampme.Module.DataBase

import com.google.firebase.database.FirebaseDatabase

object DataBaseReference {
    val mUsersDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
}