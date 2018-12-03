package com.balljoint.mktsui.recyclerview2d.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.balljoint.mktsui.recyclerview2d.R
import com.balljoint.mktsui.recyclerview2d.adapter.CategoryAdapter
import com.balljoint.mktsui.recyclerview2d.viewModel.MainViewModel



class MainFragment : Fragment() {

    private lateinit var mVM: MainViewModel
    private lateinit var mVA: CategoryAdapter
    private lateinit var mRV: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // init viewmodel
        mVM = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

//        mVM = MainViewModel(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.main_fragment, container, false)

        mRV = view.findViewById<RecyclerView>(R.id.category_recycler_view)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)

        val result = view.findViewById<LinearLayout>(R.id.result_placeholder)
        val errMsg = view.findViewById<TextView>(R.id.result_text)
        mVM.getState().observe(this, Observer {
            if (it!=null) {
                result.visibility = View.VISIBLE
                errMsg.text = resources.getString(it)
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (context!=null) {

            mVA = CategoryAdapter(context!!)

            // init Recycler View
            mRV.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = mVA
            }

            // prepare viewmodel to observe data
            mVM.getVideoList().observe(this, Observer {videos->
                videos?.let {mVA.setCategories(it)}
            })
        }

        // start to load video
        mVM.loadVideo()
    }
}
