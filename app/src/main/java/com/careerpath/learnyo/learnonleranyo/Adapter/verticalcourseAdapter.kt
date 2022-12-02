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
import com.careerpath.learnyo.learnonleranyo.Model.Course
import kotlinx.android.synthetic.main.course_view.view.*
import kotlinx.android.synthetic.main.verticalviewcourse.view.*

class verticalcourseAdapter(private val allCoursesList: ArrayList<Course>, context: Context): RecyclerView.Adapter<verticalcourseAdapter.ImageViewHolder>() {

    val ctx: Context = context



    public class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val courseImage_singleCourse: ImageView = itemView.findViewById(R.id.courseimage1)
        val CourseRating_singleCourse: RatingBar = itemView.findViewById(R.id.courserating1)
        val CoursefullName_singlecourse: TextView = itemView.findViewById(R.id.courseName1)
        val CourseCategory_singlecourse: TextView = itemView.findViewById(R.id.category1)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.course_view, parent,false))

        val CourseView = LayoutInflater.from(parent.context).inflate(R.layout.verticalviewcourse, parent,false)
        return ImageViewHolder(CourseView)
    }

    override fun getItemCount(): Int {

        return allCoursesList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val course: Course = allCoursesList[position]
        holder.CoursefullName_singlecourse.text = course.courseFullname
        holder.CourseCategory_singlecourse.text = course.courseCategory


        Glide.with(holder.itemView).load(course.imageUri).into(holder.itemView.courseimage1)

        holder.itemView.setOnClickListener {
            goCourseDetailsPage(position)
        }
    }

    private fun goCourseDetailsPage(position: Int) {
        val course: Course = allCoursesList[position]
        val intent = Intent(ctx , CoursedescriptionActivity::class.java)
        intent.putExtra("uniqueid", course.courseuniqueid)
        ctx.startActivity(intent)

    }

}