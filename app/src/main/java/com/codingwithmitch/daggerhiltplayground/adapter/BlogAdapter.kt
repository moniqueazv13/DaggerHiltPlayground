package com.codingwithmitch.daggerhiltplayground.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.codingwithmitch.daggerhiltplayground.R
import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class BlogAdapter(private val context: Context) : RecyclerView.Adapter<BlogViewHolder>() {

    var blogList = listOf<Blog>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.blog_item,
            parent,
            false
        )
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogs = blogList[position]
        holder.blogTitle.text = blogs.title
        Picasso.with(context).load(blogs.image).into(holder.blogImage)

    }

    override fun getItemCount(): Int {
        return blogList.size
    }

}

class BlogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val blogTitle by lazy { view.findViewById<TextView>(R.id.blog_text) }
    val blogImage by lazy { view.findViewById<ImageView>(R.id.blog_image) }
}
