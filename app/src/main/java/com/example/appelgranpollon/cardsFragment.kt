package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CardData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Models.OrderData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.adapters.CardsAdapter
import com.example.appelgranpollon.adapters.OrdersAdapter
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.gson.Gson


class cardsFragment : Fragment() {
    lateinit var views:View;
    lateinit var cards:ArrayList<CardData>
    lateinit var cardAdapter: CardsAdapter;
    private var recyclerView: RecyclerView?= null
    private var gridLayoutManager: GridLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        views= inflater.inflate(R.layout.fragment_cards, container, false)
        getCards()
        recyclerView = views.findViewById(R.id.RecyclerCards)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager;
        cardAdapter = CardsAdapter(views.context,cards)
        recyclerView?.adapter = cardAdapter

        return views;
    }

    fun getCards (){
        val client = Gson().fromJson(SharedPrefs(views.context).getUser(), ClientData::class.java);
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).getCardsByCustomer(client.id!!)
        val response = call.execute();
        Log.d("LOGGING","asdcomenzando");
        Log.d("LOGGING",response.body().toString());
        if(response.body().toString() == "null"){
            cards = ArrayList()
        }

        var resCards:List<CardData>?  = response.body();
        if (resCards != null) {

            cards = ArrayList(resCards);
        };
        Log.d("LOGGING",resCards.toString());


    }


}