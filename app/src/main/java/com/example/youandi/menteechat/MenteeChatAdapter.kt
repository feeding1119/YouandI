package com.example.youandi.menteechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.youandi.R
import com.example.youandi.auth.MentorLoginData

class MenteeChatAdapter ( val items: MutableList<MentorLoginData>) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView
        if(convertView==null){
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.all_list_item,parent,false)
        }

        val profileImg = convertView!!.findViewById<ImageView>(R.id.profileImageArea)
        val url = items[p0].user_profile

        Glide.with(profileImg)
            .load(url)
            .into(profileImg)

        val name = convertView!!.findViewById<TextView>(R.id.idArea)
        name.text = items[p0].user_name

        val company = convertView!!.findViewById<TextView>(R.id.companyArea)
        company.text = items[p0].last_text

        val subject = convertView!!.findViewById<TextView>(R.id.subjectArea)
        subject.text = ""


        return convertView!!
    }
}