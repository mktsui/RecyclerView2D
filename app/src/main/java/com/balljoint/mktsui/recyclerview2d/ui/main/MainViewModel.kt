package com.balljoint.mktsui.recyclerview2d.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.util.Log
import com.balljoint.mktsui.recyclerview2d.Model.Video
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var videoLD: MutableLiveData<ArrayList<Video>>

    fun getVideoList(): MutableLiveData<ArrayList<Video>> {
        if(!::videoLD.isInitialized) {
            videoLD = MutableLiveData()
        }
        return videoLD
    }

    fun loadVideo() {

        var testModel: List<Video> = arrayListOf()
        try {
            val inStream: InputStream = getApplication<Application>().assets.open("dummy_data.json")
            val inString = inStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            testModel = gson.fromJson(inString, Array<Video>::class.java).toList()


        } catch (e:Exception) {
            Log.d("Video", e.toString())
        }
        videoLD.postValue(reorderList(testModel))

    }

    private fun reorderList(mList: List<Video>): ArrayList<Video> {
        // Create an Array list with 3 empty Video data classes
        val orderedList = arrayListOf(Video(), Video(), Video())

        mList.forEach {
            when (it.category) {
                "Features" -> orderedList[0]=it
                "Movies" -> orderedList[1]=it
                "TV Shows" -> orderedList[2]=it
            }
        }

        return orderedList
    }


}



