package com.balljoint.mktsui.recyclerview2d.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.balljoint.mktsui.recyclerview2d.R
import com.balljoint.mktsui.recyclerview2d.model.Videos
import com.balljoint.mktsui.recyclerview2d.utilities.Constants
import com.balljoint.mktsui.recyclerview2d.utilities.VolleySingleton
import com.balljoint.mktsui.recyclerview2d.viewModel.MainViewModel
import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.io.InputStream
import java.lang.Exception

/*
** Model class
 */
class DataManager(context: Context, mVM: MainViewModel) {

    private val mContext = context
    private val dataCB = mVM

    // method to call API with Volley
    fun callAPI() {
        val url = Constants.API_LINK

        val jsonRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                parseJson(response.toString())
            },
            Response.ErrorListener {
                dataCB.onRetrieveDataError(R.string.ds_return_msg_fail)
            }
        )

        // call Volley to send request
        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonRequest)

    }

    // Below code were used during development to get Json objects
    /*
    fun readJson(jsonFile:String) {

        try {
            val inStream: InputStream = mContext.assets.open(jsonFile)
            val inString = inStream.bufferedReader().use { it.readText() }
            parseJson(inString)
        } catch (e: Exception) {
            dataCB.onRetrieveDataError(R.string.ds_return_msg_fail)
        }
    }
    */

    // for testing
    fun mockData(jsonString: String){
        parseJson(jsonString)
    }

    // 2nd step in the pipeline for parsing Json object from input using Gson
    private fun parseJson(jsonString: String) {
        try {
            val parsedJsonList =
                Gson().fromJson(jsonString, Array<Videos>::class.java).toList()
            reorderList(parsedJsonList)
        } catch (e: JsonParseException) {
            dataCB.onRetrieveDataError(R.string.ds_return_msg_fail)
        }
    }

    // Special treatment to make the list order same as mock up
    private fun reorderList(mList: List<Videos>) {
        // according to the mockup, only 3 lists will be displayed
        val orderedList = arrayListOf(Videos(), Videos(), Videos())
        var listCount = 3

        // Since I need to follow the order in mockup without given logic,
        // so this has to be done in hardcode manner
        mList.forEach {
            when(it.category) {
                "Features" -> {
                    orderedList[0] = it
                    listCount--
                }
                "Movies" -> {
                    orderedList[1] = it
                    listCount--
                }
                "TV Shows" -> {
                    orderedList[2] = it
                    listCount--
                }
            }
        }

        // error handling for different scenario
        when (listCount) {
            // successful data retrieve
            0 -> dataCB.onRetrieveDataSuccess(orderedList)
            // none of the 3 categories can be populated
            3 -> dataCB.onRetrieveDataError(R.string.ds_return_msg_empty)
            // only some of the categories can be populated
            else -> dataCB.onRetrieveDataError(R.string.ds_return_msg_bad_format)
        }

    }

    // for viewmodel to override callback functions
    interface OnRetrieveDataCallback {
        fun onRetrieveDataSuccess(videoList: ArrayList<Videos>)
        fun onRetrieveDataError(e: Int)
    }
}