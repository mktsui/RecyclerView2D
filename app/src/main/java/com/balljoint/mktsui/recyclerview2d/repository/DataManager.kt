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

class DataManager(context: Context, mVM: MainViewModel) {

    private val mContext = context
    private val dataCB = mVM

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

    fun readJson(jsonFile:String) {

        try {
            val inStream: InputStream = mContext.assets.open(jsonFile)
            val inString = inStream.bufferedReader().use { it.readText() }
            parseJson(inString)
        } catch (e: Exception) {
            dataCB.onRetrieveDataError(R.string.ds_return_msg_fail)
        }
    }

    fun mockData(){
        val input =
            "[{\"category\": \"\",\"items\": [{\"title\": \"\",\"year\": 0,\"description\": \"\",\"images\": {\"portrait\": \"\",\"landscape\": \"\"}}]}]"

        parseJson(input)
    }

    private fun parseJson(jsonString: String) {
        try {
            val parsedJsonList =
                Gson().fromJson(jsonString, Array<Videos>::class.java).toList()
            reorderList(parsedJsonList)
        } catch (e: JsonParseException) {
            dataCB.onRetrieveDataError(R.string.ds_return_msg_fail)
        }
    }

    private fun reorderList(mList: List<Videos>) {
        val orderedList = arrayListOf(Videos(), Videos(), Videos())
        var listCount = 3

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

        when (listCount) {
            0 -> dataCB.onRetrieveDataSuccess(orderedList)
            3 -> dataCB.onRetrieveDataError(R.string.ds_return_msg_empty)
            else -> dataCB.onRetrieveDataError(R.string.ds_return_msg_bad_format)
        }

    }

    interface OnRetrieveDataCallback {
        fun onRetrieveDataSuccess(videoList: ArrayList<Videos>)
        fun onRetrieveDataError(e: Int)
    }
}