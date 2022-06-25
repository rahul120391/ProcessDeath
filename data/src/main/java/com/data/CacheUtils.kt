package com.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheUtils @Inject constructor(@ApplicationContext val context: Context) {


    inline fun <reified T> readData(fileName:String):T?{
        try {
             val file = File(context.cacheDir,fileName)
             if(file.exists()){
                 val fr = FileReader(file.absoluteFile)
                 val json = BufferedReader(fr).readLine()
                 fr.close()
                 if (!json.isNullOrEmpty()) {
                     val cacheEntryType = object : TypeToken<T>() {}.type
                     return Gson().fromJson(json, cacheEntryType)
                 }
             }
        }
        catch (e:Exception){
            println("exception $e")
        }
        return null
    }

    fun<T> writeData(fileName: String,data:T){
        try {
               val value = Gson().toJson(data)
               val file = File(context.cacheDir,fileName)
               val fileWriter = FileWriter(file)
               val bufferWriter = BufferedWriter(fileWriter)
               bufferWriter.write(value)
               bufferWriter.close()
               fileWriter.close()
        }
        catch (e:Exception){
            println("exception $e")
        }
    }

    fun deleteFile(fileName: String){
        try {
             val file = File(context.cacheDir,fileName)
             if(file.exists()) file.delete()
        }
        catch (e:FileNotFoundException){
            print("exception ${e.message}")
        }
    }

    fun lastModifiedTime(fileName: String):Long{
        try {
            val file = File(context.cacheDir,fileName)
            if(file.exists()) return file.lastModified()
        }
        catch (e:FileNotFoundException){
            print("exception ${e.message}")
        }
        return 0L
    }

    inline fun <reified T> readDataUnderExpiration(expirationTime:Long,fileName: String):T?{
        val isExpired = System.currentTimeMillis()-lastModifiedTime(fileName)>=expirationTime
        if(isExpired) deleteFile(fileName)
        return readData<T>(fileName)
    }


}