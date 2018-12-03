package com.balljoint.mktsui.recyclerview2d

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.balljoint.mktsui.recyclerview2d.adapter.VideoAdapter
import com.balljoint.mktsui.recyclerview2d.model.Items
import com.balljoint.mktsui.recyclerview2d.ui.main.MainFragment
import com.balljoint.mktsui.recyclerview2d.ui.main.VideoDetailsFragment

/*
** Top level activity
 */
class MainActivity : AppCompatActivity(), VideoAdapter.OnVideoItemSelected {

    // To open main fragment for video catalogue view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_w_fragment)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }

    // To open video details fragment
    override fun onVideoItemSelected(video: Items) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, 0, 0, android.R.anim.fade_out)
            .add(R.id.container, VideoDetailsFragment.newInstance(video))
            .addToBackStack("videoDetails")
            .commit()
    }


}
