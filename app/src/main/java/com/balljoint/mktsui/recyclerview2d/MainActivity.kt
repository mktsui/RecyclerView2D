package com.balljoint.mktsui.recyclerview2d

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.balljoint.mktsui.recyclerview2d.ui.main.MainFragment
import com.balljoint.mktsui.recyclerview2d.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mVM:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        mVM= MainViewModel(application)
        mVM.getVideoList()
        mVM.loadVideo()
    }

}
