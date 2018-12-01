package com.balljoint.mktsui.recyclerview2d.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.balljoint.mktsui.recyclerview2d.Model.Items
import com.balljoint.mktsui.recyclerview2d.R

class VideoAdapter() : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private lateinit var mContext: Context
    private var mCategory: Int = 0
    private lateinit var mVideoList: List<Items>

    constructor(context: Context, category: Int, videoList: List<Items>) : this() {
        this.mContext = context
        this.mCategory = category
        this.mVideoList = videoList
    }

    inner class VideoViewHolder(videoListView: View) : RecyclerView.ViewHolder(videoListView)

    override fun getItemCount(): Int {
        return mVideoList.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VideoViewHolder {
        val vh:View = if (mCategory == 0) {
            // only first in list is Features list
            LayoutInflater.from(mContext).inflate(R.layout.feature_video_item, p0,false)
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.regular_video_item, p0, false)
        }
        return VideoViewHolder(vh)
    }

    override fun onBindViewHolder(p0: VideoViewHolder, p1: Int) {
        val current = mVideoList[p1]
        val videoImgView : ImageView
        val videoTitleView : TextView

        when(mCategory) {
            0 -> {
                videoImgView = p0.itemView.findViewById(R.id.landscape_image)
                videoTitleView = p0.itemView.findViewById(R.id.video_title_f)
            }
            else -> {
                videoImgView = p0.itemView.findViewById(R.id.portrait_image)
                videoTitleView = p0.itemView.findViewById(R.id.video_title_r)
            }
        }

        videoTitleView.text = current.title

    }

}