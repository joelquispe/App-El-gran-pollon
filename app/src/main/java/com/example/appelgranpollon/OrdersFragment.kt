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
import com.example.appelgranpollon.Models.CategoryData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Models.OrderData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.adapters.CategoryAdapter
import com.example.appelgranpollon.adapters.OrdersAdapter
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    lateinit var orders:ArrayList<OrderData>
    lateinit var views:View;
    lateinit var orderAdapter:OrdersAdapter;
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
        views= inflater.inflate(R.layout.fragment_orders, container, false)

        getOrders()
        recyclerView = views.findViewById(R.id.RecyclerOrders)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager;
        orderAdapter = OrdersAdapter(views.context,orders)
        recyclerView?.adapter = orderAdapter


        return views;
    }
    fun getOrders (){
        val client = Gson().fromJson(SharedPrefs(views.context).getUser(),ClientData::class.java);
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).getOrders(client.id!!)
        val response = call.execute();

            val ord = Gson().fromJson(response.errorBody()?.string(),Array<OrderData>::class.java).asList()
            Log.d("LOGGING","asd"+ord.toString());
            var resOrders:List<OrderData>?  = ord;
            if (resOrders != null) {
                var lista:List<OrderData> = ord;
                orders = ArrayList(lista);
            };
            Log.d("LOGGING",resOrders.toString());


    }



}