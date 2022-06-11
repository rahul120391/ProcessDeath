package com.data.login.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LoginDataStoreRepository {
    suspend fun saveData(isLoggedIn:Boolean)
    suspend fun readData():Boolean
}

class LoginDataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>):LoginDataStoreRepository{

    companion object{
        private val  IS_LOGGED_IN = booleanPreferencesKey("IsLoggedIn")
    }

    override suspend fun saveData(isLoggedIn: Boolean) {
        dataStore.edit {
                preferences->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    override suspend fun readData(): Boolean {
        return dataStore.data.map {
                preferences->
            preferences[IS_LOGGED_IN]
        }.flowOn(Dispatchers.IO).first()?:false
    }
}