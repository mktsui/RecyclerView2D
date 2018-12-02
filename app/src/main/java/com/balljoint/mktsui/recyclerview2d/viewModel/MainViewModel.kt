package com.balljoint.mktsui.recyclerview2d.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.balljoint.mktsui.recyclerview2d.model.Videos
import com.balljoint.mktsui.recyclerview2d.utilities.Constants
import com.google.gson.Gson
import java.io.InputStream
import java.lang.Exception
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var videoLD: MutableLiveData<ArrayList<Videos>>

    fun getVideoList(): MutableLiveData<ArrayList<Videos>> {
        if(!::videoLD.isInitialized) {
            videoLD = MutableLiveData()
        }
        return videoLD
    }

    private fun readJson(jsonFile:String): List<Videos> {

        var testModel = emptyList<Videos>()
        try {
            val inStream: InputStream = getApplication<Application>().assets.open(jsonFile)
            val inString = inStream.bufferedReader().use { it.readText() }
            testModel = Gson().fromJson(inString, Array<Videos>::class.java).toList()
        } catch (e:Exception) {
            Log.d("Video", e.toString())
        }

        return testModel
    }

    private fun reorderList(mList: List<Videos>): ArrayList<Videos> {
        val orderedList = arrayListOf(Videos(), Videos(), Videos())

        mList.forEach {
            when(it.category) {
                "Features" -> orderedList[0] = it
                "Movies" -> orderedList[1] = it
                "TV Shows" -> orderedList[2] = it
            }
        }

        return orderedList
    }

    fun loadVideo() {
        val resultList= reorderList(readJson(Constants.DUMMY_DATA_CORRECT))
        videoLD.postValue(resultList)
    }

}



