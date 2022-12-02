package com.careerpath.learnyo.learnonleranyo.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.CoursedescriptionActivity
import com.careerpath.learnyo.learnonleranyo.Model.Content
import com.careerpath.learnyo.learnonleranyo.Model.Course
import kotlinx.android.synthetic.main.contentviewrecycler.view.*
import kotlinx.android.synthetic.main.course_view.view.*

class contentAdapter(private val allcontentList: ArrayList<Content>): RecyclerView.Adapter<contentAdapter.ImageViewHolder>() {


    public class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val contenttxt: TextView = itemView.findViewById(R.id.textView)
        val contenthead: TextView = itemView.findViewById(R.id.headingview)
        val contentimage: ImageView = itemView.findViewById(R.id.imageview3)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.course_view, parent,false))

        val ContentView = LayoutInflater.from(parent.context).inflate(R.layout.contentviewrecycler, parent,false)
        return ImageViewHolder(ContentView)
    }

    override fun getItemCount(): Int {
        return allcontentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val content: Content = allcontentList[position]
        if(content.contentText != ".") {
            holder.contenttxt.visibility = View.VISIBLE

            holder.contenttxt.text = content.contentText
        }

        if(content.contentheading != ".") {
            holder.contenthead.visibility = View.VISIBLE
            holder.contenthead.text = content.contentheading
        }


        if (content.contentUri != ".") {

            Glide.with(holder.itemView).load(content.contentUri).into(holder.itemView.imageview3)
        }


    }

}