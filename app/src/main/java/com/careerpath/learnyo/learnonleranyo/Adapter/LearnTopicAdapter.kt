package com.careerpath.learnyo.learnonleranyo.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.careerpath.learnyo.R
import com.careerpath.learnyo.learnonleranyo.TopicContentActivity
import com.careerpath.learnyo.learnonleranyo.Model.Topicmodel

class LearnTopicAdapter (private val allTopicList: ArrayList<Topicmodel>, context: Context,val theuniquenam:String,val coursename:String): RecyclerView.Adapter<LearnTopicAdapter.TopicViewHolder>() {
    val ctx: Context = context
    class TopicViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.nameetopic)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnTopicAdapter.TopicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.learntopic_view, parent, false)

        return LearnTopicAdapter.TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val ItemsViewModel : Topicmodel = allTopicList[position]

        holder.textView.text = ItemsViewModel.topicname
        holder.itemView.setOnClickListener {
            goAddContentPage(position)
        }
    }




    override fun getItemCount(): Int {
        return allTopicList.size


    }

    private fun goAddContentPage(position: Int) {
        val topic: Topicmodel = allTopicList[position]
        val uniqueName: String = theuniquenam
        val intent = Intent(ctx, TopicContentActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("currenttopic", topic.topicname)

        intent.putExtra("nameUnique", uniqueName)
        ctx.startActivity(intent)
    }



}