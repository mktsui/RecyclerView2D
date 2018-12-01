package com.balljoint.mktsui.recyclerview2d

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.balljoint.mktsui.recyclerview2d.Adapter.CategoryAdapter
import com.balljoint.mktsui.recyclerview2d.ui.main.MainFragment
import com.balljoint.mktsui.recyclerview2d.ViewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mVM: MainViewModel
    private lateinit var mVA: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        mVA = CategoryAdapter(this)

        // init Recycler View
        findViewById<RecyclerView>(R.id.category_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mVA
        }

        // prepare viewmodel to observe data
        mVM = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mVM.getVideoList().observe(this, Observer {videos->
            videos?.let {mVA.setCategories(it)}
        })

        // start to load video
        mVM.loadVideo()

//        TODO("handle orientation change")
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }

    }

}
