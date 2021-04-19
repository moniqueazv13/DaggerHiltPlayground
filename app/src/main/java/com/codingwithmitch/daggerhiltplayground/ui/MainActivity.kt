package com.codingwithmitch.daggerhiltplayground.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.codingwithmitch.daggerhiltplayground.R
import com.codingwithmitch.daggerhiltplayground.adapter.BlogAdapter
import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG: String = "AppDebug"

    private val viewModel: MainViewModel by viewModels()

    private val recycler by lazy { findViewById<RecyclerView>(R.id.recycler) }

    private val blogsAdapter by lazy { BlogAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        recycler.adapter = blogsAdapter
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
//                    appendBlogTitles(dataState.data)
                    blogsAdapter.blogList = dataState.data
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                   val textError =  dataState.exception.message.toString()
                    Log.e(textError, "ERRO")
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

//    private fun displayError(message: String?) {
//        if (message != null) text.text = message else text.text = "Unknown error."
//    }


    //TODO: criar recycler
//    private fun appendBlogTitles(blogs: List<Blog>) {
//        val sb = StringBuilder()
//        for (blog in blogs) {
//            sb.append(blog.title + "\n")
////            sb.append(blog.body + "\n")
//        }
//        text.text = sb.toString()
////        body.text = sb.toString()
//    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

}



















