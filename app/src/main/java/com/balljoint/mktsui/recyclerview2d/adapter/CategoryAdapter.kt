package com.balljoint.mktsui.recyclerview2d.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.balljoint.mktsui.recyclerview2d.adapter.CategoryAdapter.CategoryViewHolder
import com.balljoint.mktsui.recyclerview2d.model.Videos
import com.balljoint.mktsui.recyclerview2d.R

class CategoryAdapter(context: Context) : RecyclerView.Adapter<CategoryViewHolder>() {

    private var dataList = arrayListOf<Videos>()
    private val mContext = context

    internal fun setCategories(mList:ArrayList<Videos>) {
        this.dataList = mList
        notifyDataSetChanged()
        Log.d("Video", "Update dataset")
    }

    inner class CategoryViewHolder(catView: View) : RecyclerView.ViewHolder(catView)

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryViewHolder {
        val vh = LayoutInflater.from(mContext).inflate(R.layout.list_video_cat, p0, false)
        return CategoryViewHolder(vh)
    }

    override fun onBindViewHolder(p0: CategoryViewHolder, p1: Int) {
        val current = dataList[p1]
        val categoryTitleView = p0.itemView.findViewById<TextView>(R.id.categoryTitle)
        val videoRCView = p0.itemView.findViewById<RecyclerView>(R.id.video_item_recycle_view)

        categoryTitleView.text = current.category
        videoRCView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = VideoAdapter(mContext, p1, current.items)
        }

    }
}