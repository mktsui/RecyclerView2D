package com.balljoint.mktsui.recyclerview2d.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.balljoint.mktsui.recyclerview2d.model.Videos
import com.balljoint.mktsui.recyclerview2d.repository.DataManager
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application), DataManager.OnRetrieveDataCallback {
    private lateinit var videoLD: MutableLiveData<ArrayList<Videos>>
    private val badResponse = MutableLiveData<Int>()
    private val mContext = application


    fun getVideoList(): MutableLiveData<ArrayList<Videos>> {
        if(!::videoLD.isInitialized) {
            videoLD = MutableLiveData()
        }
        return videoLD
    }

    fun getState(): LiveData<Int> {
        return badResponse
    }

    override fun onRetrieveDataSuccess(videoList: ArrayList<Videos>){
        videoLD.postValue(videoList)
    }

    override fun onRetrieveDataError(e: Int) {
        badResponse.postValue(e)
    }

    fun loadVideo() {
//        DataManager(mContext, this).mockData()
//        DataManager(mContext, this).readJson(Constants.DUMMY_DATA_CORRECT)
        DataManager(mContext, this).callAPI()
    }

}



