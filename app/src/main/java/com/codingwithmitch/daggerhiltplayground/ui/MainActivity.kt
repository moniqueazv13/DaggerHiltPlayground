package com.codingwithmitch.daggerhiltplayground.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.codingwithmitch.daggerhiltplayground.R
import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.util.DataState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG: String = "AppDebug"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) text.text = message else text.text = "Unknown error."
    }

    private fun appendBlogTitles(blogs: List<Blog>) {
        val sb = StringBuilder()
        for (blog in blogs) {
            sb.append(blog.title + "\n")
//            sb.append(blog.body + "\n")
        }
        text.text = sb.toString()
//        body.text = sb.toString()
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

}



















