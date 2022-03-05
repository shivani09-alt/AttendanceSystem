package com.example.attendancesystemmanagement

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancesystemmanagement.ViewListAdapter.*
import java.util.*
import kotlin.random.Random

class ViewListAdapter (val context: Context,val arrayList: ArrayList<Users>): RecyclerView.Adapter<ViewHolder>() {



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val userName:TextView=itemView.findViewById(R.id.userName)
      val email:TextView=itemView.findViewById(R.id.userEmail)
      val userNameLetter:TextView=itemView.findViewById(R.id.userNameLetter)
      val mainLayout:LinearLayout=itemView.findViewById(R.id.mainLayout);


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val random = Random
        val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        holder.email.text=arrayList.get(position).email
        holder.userName.text=arrayList.get(position).name
        holder.userNameLetter.text=arrayList.get(position).name.substring(0,1).uppercase()
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        shape.setColor(color)
        holder.userNameLetter.setBackground(shape)
        holder.mainLayout.setOnClickListener{ view ->
            val bundle:Bundle= Bundle();
            bundle.putString("id",arrayList.get(position).id)
            view.findNavController().navigate(R.id.viewUserData,bundle)
        }

    }

    override fun getItemCount(): Int {

        return arrayList.size
    }
}