package com.knear.android.user.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knear.android.user.data.entities.UserInformationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_information ORDER BY accountId ASC")
    fun getUserInformation(): Flow<List<UserInformationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInformation(userInformation: UserInformationEntity)

    @Query("DELETE FROM user_information")
    suspend fun deleteAll():Unit

}