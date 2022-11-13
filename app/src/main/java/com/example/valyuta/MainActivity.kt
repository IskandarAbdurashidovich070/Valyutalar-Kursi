package com.example.valyuta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.valyuta.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var requestQueue: RequestQueue
    lateinit var userAdapter: MyRvAdapter
    lateinit var myNetworkHelper: MyNetworkHelper
    private lateinit var list: ArrayList<Valyuta>
            var url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myNetworkHelper = MyNetworkHelper(this)

        if(myNetworkHelper.isNetworkConnected()){

            binding.animation.visibility = View.GONE
            binding.net.visibility = View.GONE
            requestQueue = Volley.newRequestQueue(this)
            getValyut()
            doInBackground()

        }else{

            binding.animation.visibility = View.VISIBLE
            binding.net.visibility = View.VISIBLE
        }



    }

    fun getValyut(){
        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true //qanday ma'lumot kelayotganini Logda ko'rsatib turadi

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {

                    val type = object : TypeToken<List<Valyuta>>() {}.type
                    val list = Gson().fromJson<List<Valyuta>>(response.toString(), type)

                    userAdapter = MyRvAdapter(list, this@MainActivity)
                    binding.rv.adapter = userAdapter

                    Log.d(TAG, "onResponse : ${response.toString()}")
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }
            })

        jsonArrayRequest.tag = "tag1" //tag berilyapti
        requestQueue.add(jsonArrayRequest)

        requestQueue.cancelAll("tag1") // tag1 dagi so'rovlarni ortga qaytarish uchun
    }

    fun doInBackground(vararg params: Void?): Void? {

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {

                    list = ArrayList()
                    val type = object : TypeToken<List<Valyuta>>() {}.type
                    list = Gson().fromJson<List<Valyuta>>(response.toString(), type) as ArrayList<Valyuta>
                    userAdapter = MyRvAdapter(list, this@MainActivity)
                    binding.rv.adapter = userAdapter
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(binding.root.context, "$error", Toast.LENGTH_SHORT).show()
                }
            })

        requestQueue.add(jsonArrayRequest)
        return null
    }
}