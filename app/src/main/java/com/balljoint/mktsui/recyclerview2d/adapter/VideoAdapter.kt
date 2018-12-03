package com.balljoint.mktsui.recyclerview2d.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView
import com.balljoint.mktsui.recyclerview2d.model.Items
import com.balljoint.mktsui.recyclerview2d.R
import com.balljoint.mktsui.recyclerview2d.utilities.VolleySingleton

class VideoAdapter() : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private lateinit var mContext: Context
    private var mCategory: Int = 0
    private lateinit var mVideoList: List<Items>
    private lateinit var mListener: OnVideoItemSelected

    constructor(context: Context, category: Int, videoList: List<Items>) : this() {
        if (context is OnVideoItemSelected) {
            mListener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnVideoItemSelected")
        }
        this.mContext = context
        this.mCategory = category
        this.mVideoList = videoList
    }

    inner class VideoViewHolder(videoListView: View): RecyclerView.ViewHolder(videoListView)

    override fun getItemCount(): Int {
        return mVideoList.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VideoViewHolder {
        val vh:View = if (mCategory == 0) {
            // only first in list is Features list, and the rest are regular, two separate layouts are used
            LayoutInflater.from(mContext).inflate(R.layout.feature_video_item, p0,false)
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.regular_video_item, p0, false)
        }
        return VideoViewHolder(vh)
    }

    override fun onBindViewHolder(p0: VideoViewHolder, p1: Int) {
        val currentItem = mVideoList[p1]
        val videoImgView : NetworkImageView
        val videoTitleView : TextView
        val imgURL : String

        // only first in list will display landscape img
        when(mCategory) {
            0 -> {
                videoImgView = p0.itemView.findViewById(R.id.landscape_image)
                videoTitleView = p0.itemView.findViewById(R.id.video_title_f)
                imgURL = currentItem.images.landscape
            }
            else -> {
                videoImgView = p0.itemView.findViewById(R.id.portrait_image)
                videoTitleView = p0.itemView.findViewById(R.id.video_title_r)
                imgURL = currentItem.images.portrait
            }
        }

        videoTitleView.text = currentItem.title

        // Volley would manage to download image asynchronously, no specific threading management is required
        videoImgView.setImageUrl(imgURL, VolleySingleton.getInstance(this.mContext).imageLoader)

        p0.itemView.setOnClickListener{mListener.onVideoItemSelected(currentItem)}
    }

    // call back when any video item is selected
    interface OnVideoItemSelected {
        fun onVideoItemSelected(video: Items)
    }

}
