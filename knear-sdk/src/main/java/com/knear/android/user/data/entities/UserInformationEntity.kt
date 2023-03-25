package com.knear.android.user.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_information")
data class UserInformationEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "accountId") val accountId:String,
    @ColumnInfo(name = "publicKey") val publicKey:String,
    @ColumnInfo(name = "isLoggedIn") val isLoggedIn:Boolean
)
