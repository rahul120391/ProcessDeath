package com.data.login.repositories

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.data.di.SharedPrefManagerClass
import javax.inject.Inject

interface LoginDataStoreRepository {
    suspend fun saveData(isLoggedIn:Boolean)
    suspend fun readData():Boolean
}

class LoginDataStoreRepositoryImpl @Inject constructor(private val sharedPrefManagerClass: SharedPrefManagerClass):LoginDataStoreRepository{

    companion object{
        private val  IS_LOGGED_IN = booleanPreferencesKey("IsLoggedIn")
    }

    override suspend fun saveData(isLoggedIn: Boolean) {
        sharedPrefManagerClass.saveValue(IS_LOGGED_IN,isLoggedIn)
    }



    override suspend fun readData(): Boolean {
        return sharedPrefManagerClass.getValue(IS_LOGGED_IN,false)
    }
}