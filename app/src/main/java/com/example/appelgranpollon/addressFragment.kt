package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.AddressData
import com.example.appelgranpollon.Models.CardData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.adapters.AddressAdapter
import com.example.appelgranpollon.adapters.CardsAdapter
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class addressFragment : Fragment() {

    lateinit var views:View;
    lateinit var addresses:ArrayList<AddressData>
    lateinit var addressAdapter: AddressAdapter;
    private var recyclerView: RecyclerView?= null
    private var gridLayoutManager: GridLayoutManager?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_address, container, false)
        getAddresses()
        recyclerView = views.findViewById(R.id.recyclerViewAddress)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager;
        addressAdapter = AddressAdapter(views.context,addresses)
        recyclerView?.adapter = addressAdapter
        return  views;
    }
    fun getAddresses (){
        val client = Gson().fromJson(SharedPrefs(views.context).getUser(), ClientData::class.java);
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).getAddressByCustomer(client.id!!)
        val response = call.execute();
        Log.d("LOGGING","asdcomenzando");
        Log.d("LOGGING",response.body().toString());


        var resAddresses:List<AddressData>?  = response.body();
        if (resAddresses != null) {

            addresses = ArrayList(resAddresses);
        };
        Log.d("LOGGING",resAddresses.toString());


    }

}