package com.example.educationsupport.adapters.learner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R
import com.example.educationsupport.constants.Constants
import com.example.educationsupport.learner.ViewCourseActivity
import com.example.educationsupport.model.Course
import kotlin.random.Random

class HomeCourseCardAdapter(private val coursesDataset: ArrayList<Course>, private val context: Context) :
    RecyclerView.Adapter<HomeCourseCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var linearLayout: LinearLayout
         var cardIcon: ImageView
         var cardName: TextView

        init {
            linearLayout = itemView.findViewById(R.id.ll_course_card_background)
            cardIcon = itemView.findViewById(R.id.ll_course_card_icon)
            cardName = itemView.findViewById(R.id.ll_course_card_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.home_course_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = coursesDataset[position]

        holder.cardName.text = course.name
        holder.cardIcon.setImageResource(getCardImage(Random.nextInt(1, 5)))

        holder.linearLayout.setOnClickListener {
            val intent = Intent(context, ViewCourseActivity::class.java)
            intent.putExtra(Constants.COURSE_ID, course.id.toString())
            context.startActivity(intent)
        }
    }

    private fun getCardImage(nextInt: Int): Int {
        return when (nextInt) {
            1 -> R.drawable.learner_home_card_1
            2 -> R.drawable.learner_home_card_2
            3 -> R.drawable.learner_home_card_3
            4 -> R.drawable.learner_home_card_4
            else -> R.drawable.bg_course_view
        }
    }

    override fun getItemCount(): Int {
        return coursesDataset.size
    }
}