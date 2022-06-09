package com.example.youandi.menteechat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.youandi.R
import com.example.youandi.mypage.myClass

class ChatListViewAdapter(val context : Context, val items: MutableList<ChatData>) : BaseAdapter() {
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
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.chat_list_item,parent,false)
        }


        if(myClass.other_profile == "admin"){
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.adminchat_list_item,parent,false)
            val text = convertView!!.findViewById<TextView>(R.id.textArea)
            text.text = items[p0].text

            val profileImg = convertView!!.findViewById<ImageView>(R.id.profileImageArea)
            profileImg.setImageResource(R.drawable.adminchat)

        }else{
            if(items[p0].sender_index==myClass.user_index&&items[p0].receiver_index==myClass.other_index){
                convertView = LayoutInflater.from(parent?.context).inflate(R.layout.chat_list_item2,parent,false)
                val text = convertView!!.findViewById<TextView>(R.id.textArea)
                text.text = items[p0].text
                val profileImg = convertView!!.findViewById<ImageView>(R.id.profileImageArea)
                val url = myClass.user_profile


                Glide.with(profileImg)
                    .load(url)
                    .into(profileImg)
            }else if(items[p0].sender_index==myClass.other_index&&items[p0].receiver_index==myClass.user_index)
            {
                convertView = LayoutInflater.from(parent?.context).inflate(R.layout.chat_list_item,parent,false)
                val text = convertView!!.findViewById<TextView>(R.id.textArea)
                text.text = items[p0].text

                val profileImg = convertView!!.findViewById<ImageView>(R.id.profileImageArea)
                val url = myClass.other_profile

                Glide.with(profileImg)
                    .load(url)
                    .into(profileImg)
            }
        }


        return convertView!!
    }
}