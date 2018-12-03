package com.balljoint.mktsui.recyclerview2d.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.balljoint.mktsui.recyclerview2d.model.Videos
import com.balljoint.mktsui.recyclerview2d.repository.DataManager
import kotlin.collections.ArrayList

/*
** Viewmodel class
 */
class MainViewModel(application: Application) : AndroidViewModel(application), DataManager.OnRetrieveDataCallback {

    private lateinit var videoLD: MutableLiveData<ArrayList<Videos>>
    private val badResponse = MutableLiveData<Int>()
    private val mContext = application

    // make sure only one videLD livedata obj is created
    fun getVideoList(): MutableLiveData<ArrayList<Videos>> {
        if(!::videoLD.isInitialized) {
            videoLD = MutableLiveData()
        }
        return videoLD
    }

    // get method when there is bad API response
    fun getState(): LiveData<Int> {
        return this.badResponse
    }

    // interface override method for success data response
    override fun onRetrieveDataSuccess(videoList: ArrayList<Videos>){
        videoLD.postValue(videoList)
    }

    // interface overrider method for bad data response
    override fun onRetrieveDataError(e: Int) {
        badResponse.postValue(e)
    }

    // trigger method to get data from data manager
    fun loadVideo() {
//        This was used during development for easier Json access
//        DataManager(mContext, this).readJson(Constants.DUMMY_DATA_CORRECT)
        DataManager(mContext, this).callAPI()
    }

    // for testing
    fun loadMock(jsonString: String) {
        DataManager(mContext, this).mockData(jsonString)
    }

}



