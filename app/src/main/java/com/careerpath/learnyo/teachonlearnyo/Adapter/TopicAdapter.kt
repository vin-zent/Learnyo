package com.careerpath.learnyo.teachonlearnyo.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.CoursedescriptionActivity
import com.careerpath.learnyo.learnonleranyo.Model.Course
import com.careerpath.learnyo.teachonlearnyo.Model.Topic
import com.careerpath.learnyo.teachonlearnyo.CourseContentaddActivity

class TopicAdapter(private val topics: ArrayList<Topic>,context: Context,val theuniquename:String) : RecyclerView.Adapter<TopicAdapter.ViewHolder>(){
    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.topicview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicAdapter.ViewHolder, position: Int) {

        val ItemsViewModel = topics[position]

        holder.textView.text = ItemsViewModel.topicname
        holder.itemView.setOnClickListener {
            goAddContentPage(position)
        }
    }

    private fun goAddContentPage(position: Int) {
        val topic: Topic = topics[position]
        val uniquename : String = theuniquename
        val intent = Intent(ctx , CourseContentaddActivity::class.java)
        intent.putExtra("currentTopic", topic.topicname)
        intent.putExtra("nameunique", uniquename)
        ctx.startActivity(intent)

    }

    override fun getItemCount(): Int {
        return topics.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val textView: TextView = itemView.findViewById(R.id.topicnamee)


    }
}