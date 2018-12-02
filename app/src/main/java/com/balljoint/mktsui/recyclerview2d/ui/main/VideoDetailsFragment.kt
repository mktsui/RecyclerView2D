package com.balljoint.mktsui.recyclerview2d.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.balljoint.mktsui.recyclerview2d.R
import com.balljoint.mktsui.recyclerview2d.model.Items

class VideoDetailsFragment : Fragment() {

    companion object {
        private const val VIDEO = "video"

        fun newInstance(video: Items) : VideoDetailsFragment{
            val args = Bundle()
            args.putString("title", video.title)
            args.putString("imgSrc", video.images.landscape)
            args.putInt("year", video.year)
            args.putString("desc", video.description)
            val fragment = VideoDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.video_details_fragment, container, false)

        val imgView = view.findViewById<ImageView>(R.id.detail_img)
        val yearText = view.findViewById<TextView>(R.id.detail_year)
        val descText = view.findViewById<TextView>(R.id.detail_desc)
        val titleText = view.findViewById<TextView>(R.id.detail_title)

        if (arguments!=null) {

            yearText.text = arguments!!.getInt("year").toString()
            descText.text = arguments!!.getString("desc")
            titleText.text = arguments!!.getString("title")

        }

        val closeBtn = view.findViewById<ImageButton>(R.id.detail_close_btn)
        closeBtn.setOnClickListener(clickListener)

        return view
    }

    private val clickListener = View.OnClickListener { view ->

        when (view.id) {
            R.id.detail_close_btn ->
                {
                    activity!!.supportFragmentManager.popBackStack()
                }
        }
    }

}
