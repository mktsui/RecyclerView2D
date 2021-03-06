package com.balljoint.mktsui.recyclerview2d.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView
import com.balljoint.mktsui.recyclerview2d.R
import com.balljoint.mktsui.recyclerview2d.model.Items
import com.balljoint.mktsui.recyclerview2d.utilities.VolleySingleton

/*
** View for video details
 */
class VideoDetailsFragment : Fragment() {

    companion object {
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

        val imgView = view.findViewById<NetworkImageView>(R.id.detail_img)
        val yearText = view.findViewById<TextView>(R.id.detail_year)
        val descText = view.findViewById<TextView>(R.id.detail_desc)
        val titleText = view.findViewById<TextView>(R.id.detail_title)

        if (arguments!=null) {

            yearText.text = arguments!!.getInt("year").toString()
            descText.text = arguments!!.getString("desc")
            titleText.text = arguments!!.getString("title")
            // Volley would manage and try retrieving image from cache
            imgView.setImageUrl(arguments!!.getString("imgSrc"),
                VolleySingleton.getInstance(this.context!!).imageLoader)
        }

        // close button at top right corner
        val closeBtn = view.findViewById<ImageButton>(R.id.detail_close_btn)
        closeBtn.setOnClickListener(clickListener)

        return view
    }

    // close button click listener
    private val clickListener = View.OnClickListener { view ->

        when (view.id) {
            R.id.detail_close_btn ->
                {
                    activity!!.supportFragmentManager.popBackStack()
                }
        }
    }

}
