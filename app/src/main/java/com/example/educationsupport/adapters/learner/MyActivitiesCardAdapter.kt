package com.example.educationsupport.adapters.learner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationsupport.R

class MyActivitiesCardAdapter(private val myActivitiesDataset: Array<String>, private val context: Context) :
    RecyclerView.Adapter<MyActivitiesCardAdapter.ViewHolder?>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var linearLayout: LinearLayout
        private var cardIcon: ImageView
        var cardName: TextView

        init {
            linearLayout = itemView.findViewById(R.id.ll_my_activities_card)
            cardIcon = itemView.findViewById(R.id.ll_my_activity_card_icon)
            cardName = itemView.findViewById(R.id.ll_my_activity_card_name)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.home_my_activities_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO: Need to handle OnClickListener
        holder.cardName.text = myActivitiesDataset[position]
    }

    override fun getItemCount(): Int {
        return myActivitiesDataset.size
    }
}