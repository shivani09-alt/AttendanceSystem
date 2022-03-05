package com.example.attendancesystemmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class ViewUserList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
      val view:View = inflater.inflate(R.layout.view_user_list, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "View Student Details"
        val recyclerView:RecyclerView=view.findViewById(R.id.recyclerview)
        val swipeRefreshLayout:SwipeRefreshLayout=view.findViewById(R.id.refreshLayout)
        val type: String? = arguments?.getString("type")
        val dbHandler = DBHandler(activity as AppCompatActivity)
        var list:ArrayList<Users> = dbHandler.getUserList(type.toString())
        var adapter =ViewListAdapter(activity as AppCompatActivity,list)
        recyclerView.layoutManager=LinearLayoutManager(activity)
         recyclerView.adapter=adapter
        swipeRefreshLayout.setOnRefreshListener {
          list=dbHandler.getUserList(type.toString())
          adapter = ViewListAdapter(activity as AppCompatActivity,list)
          recyclerView.adapter=adapter
          swipeRefreshLayout.isRefreshing=false
        }
        return view
    }

}