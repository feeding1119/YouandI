package com.example.youandi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.serverdata.Mentor
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom

class CardStackAdapter(val context: Context, val items: MutableList<MentorLoginData>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val profileImg = itemView.findViewById<ImageView>(R.id.profileImageArea)
        val name = itemView.findViewById<TextView>(R.id.itemName)
        val company= itemView.findViewById<TextView>(R.id.itemCompany)
        val subject= itemView.findViewById<TextView>(R.id.itemSubject)
        var shortIntro = itemView.findViewById<TextView>(R.id.itemShortIntro)

        fun binding(data: MentorLoginData){
            Glide.with(profileImg)
                .load(data.user_profile)
                .into(profileImg)
            name.text=data.user_name
            company.text=data.user_company
            subject.text=data.user_subject
            shortIntro.text=data.user_short

        }
    }
}