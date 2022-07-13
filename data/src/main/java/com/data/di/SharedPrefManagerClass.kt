package com.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManagerClass @Inject constructor(val dataStore: DataStore<Preferences>){


    suspend inline fun<reified T> saveValue(key:Preferences.Key<T>,value:T){
        dataStore.edit {
                preferences->
            preferences[key] = value
        }
    }

    suspend inline fun<reified T> getValue(key:Preferences.Key<T>,defaultValue:T):T = dataStore.data.map {
                preferences->
            preferences[key]
        }.flowOn(Dispatchers.IO).first()?:defaultValue

}