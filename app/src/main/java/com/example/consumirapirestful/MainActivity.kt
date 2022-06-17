package com.example.consumirapirestful

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var data:String = ""
    override fun onClick(view: View?) {

            var textView = findViewById<TextView>(R.id.txtresponse)
            textView.movementMethod = ScrollingMovementMethod()
            val url = "https://gorest.co.in/public/v1/users"

        val request = StringRequest(
            Request.Method.GET, // method
            url, // url // json request
            {response -> // response listener
                try {
                    textView.text = ""
                    data = response
                    data = data.substring(212, data.length-1)
                    var JArray = JSONArray(data)
                    for (i in 0..JArray.length()-1) {
                        var JObject = JArray.getJSONObject(i)

                        val id: Int = JObject.getInt("id")
                        val name: String = JObject.getString("name")
                        val email: String = JObject.getString("email")
                        val gender: String = JObject.getString("gender")
                        val status: String = JObject.getString("status")

                        textView.append("Id: $id\nName: $name\nEmail : $email\nGender: $gender \nStatus: $status \n\n")
                    }

                }catch (e: JSONException){
                    textView.text = e.message
                }
            },
            {error -> // error listener
                textView.text = error.message
            }
        )
        MySingleton.getInstance(applicationContext).addToRequestQueue(request)
    }
}