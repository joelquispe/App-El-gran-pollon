package com.example.appelgranpollon

import android.os.Bundle
import android.os.Handler.Callback
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.appelgranpollon.Models.CardData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response


class CardFragment : Fragment() {
    lateinit var client:ClientData
    lateinit var views:View;
    lateinit var inputNumberCard:EditText;
    lateinit var inputCvv:EditText;
    lateinit var inputExpirationDate:EditText;
    lateinit var btnSave:Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        views = inflater.inflate(R.layout.fragment_card, container, false)
        inputNumberCard = views.findViewById<EditText>(R.id.inputNumeroTarjeta)
        inputCvv = views.findViewById<EditText>(R.id.inputCVV)
        inputExpirationDate  = views.findViewById<EditText>(R.id.inputVencimiento)
        btnSave = views.findViewById<Button>(R.id.btnSaveCard)
        btnSave.setOnClickListener {
            createCard()
        }

        getUser(views)
        return views;
    }
    private fun getUser(view:View){
        var data =  SharedPrefs(view.context).getUser();
        client = Gson().fromJson(data, ClientData::class.java)

        if(client != null){
            Log.d("LOGGING",client.toString())
        }
    }
    private fun createCard(){
        val card:CardData = CardData(expire_date = inputExpirationDate.text.toString(), number = inputNumberCard.text.toString(), cc_num = inputCvv.text.toString(), cliente = ClientData(id =client.id ));

        val call = RestEngine.getRestEngine().create(ApiClient::class.java).createCard(card);
        call.enqueue(object:retrofit2.Callback<CardData>{
            override fun onResponse(call: Call<CardData>, response: Response<CardData>) {
                Log.d("LOGGGING",response.toString())
                Navigation.findNavController(views).navigate(R.id.profileFragment);
            }

            override fun onFailure(call: Call<CardData>, t: Throwable) {
                Log.d("LOGGGING","err")
            }
        })

    }

}