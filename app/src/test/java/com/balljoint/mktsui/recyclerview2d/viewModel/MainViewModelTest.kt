package com.balljoint.mktsui.recyclerview2d.viewModel

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.content.Context
import com.balljoint.mktsui.recyclerview2d.R
import com.balljoint.mktsui.recyclerview2d.repository.DataManager

import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private var dataManager = mock(DataManager::class.java)
    private var mVM = mock(MainViewModel::class.java)
    private val mContext = mock(Context::class.java)
    private val mApplication = mock(Application::class.java)

    // This Json has all the keys but no values
    private val emptyJsonString = "[{\"category\": \"\",\"items\": [{\"title\": \"\",\"year\": 0,\"description\": \"\",\"images\": {\"portrait\": \"\",\"landscape\": \"\"}}]}]"

    // This Json has only one required category: Features, and missing TV Shows and Movies
    private val unsupportedJsonString = "[{\"category\": \"Features\",\"items\": [{\"title\": \"\",\"year\": 0,\"description\": \"\",\"images\": {\"portrait\": \"\",\"landscape\": \"\"}}]}]"

    // This is an incorrect Json for this app
    private val badJsonString = "{}"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mVM = MainViewModel(mApplication)
        dataManager = DataManager(mContext, mVM)
    }

    // Test for empty Json, should give "No result" error
    @Test
    fun fetchEmptyData() {
        val observer = mock(Observer::class.java) as Observer<Int>
        mVM.getState().observeForever(observer)

        //when
        mVM.loadMock(emptyJsonString)

        // then
        verify(observer).onChanged(R.string.ds_return_msg_empty)
    }

    // Test for incompleted Json, should give "Unsupported format" error
    @Test
    fun fetchUnsupportedData() {
        val observer = mock(Observer::class.java) as Observer<Int>
        mVM.getState().observeForever(observer)

        //when
        mVM.loadMock(unsupportedJsonString)

        // then
        verify(observer).onChanged(R.string.ds_return_msg_bad_format)
    }

    // Test for bad API result, should give "API error" error
    @Test
    fun fetchData_Error() {
        val observer = mock(Observer::class.java) as Observer<Int>
        mVM.getState().observeForever(observer)

        //when
        mVM.loadMock(badJsonString)

        // then
        verify(observer).onChanged(R.string.ds_return_msg_fail)
    }
}