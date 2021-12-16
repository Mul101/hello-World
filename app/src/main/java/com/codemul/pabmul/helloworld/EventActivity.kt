package com.codemul.pabmul.helloworld

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemul.pabmul.helloworld.data.Event
import com.codemul.pabmul.helloworld.db.RealtimeDatabase
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class EventActivity : AppCompatActivity() {
    private lateinit var adapterEvent: EventAdapter
    private lateinit var rvEvent : RecyclerView

    private val db = RealtimeDatabase.instance()
    private var storage : FirebaseStorage? = null
    private var databaseRef: DatabaseReference? = null
    private var database : FirebaseDatabase? = null
    private var dbListener:ValueEventListener? = null
    private val listKey = mutableListOf<String>()
    private val listEvent = mutableListOf<Event>()
    private lateinit var  eventList : MutableList<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        rvEvent = findViewById(R.id.rv_event)

        getData()
    }



    private fun getData(){
        rvEvent.setHasFixedSize(true)
        rvEvent.layoutManager = LinearLayoutManager(this@EventActivity, LinearLayoutManager.VERTICAL, false)

        eventList = ArrayList()
        adapterEvent = EventAdapter(this, eventList)
        rvEvent.adapter = adapterEvent


        storage = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("event")
        dbListener = databaseRef?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                for (eventSnapshot in snapshot.children){
                    val upload = eventSnapshot.getValue(Event::class.java)
                    upload!!.id = eventSnapshot.key
                    eventList.add(upload)
                }

                adapterEvent.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EventActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun initRecylerView() {
        rvEvent.apply {
            layoutManager = LinearLayoutManager(this@EventActivity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterEvent
        }
    }
}