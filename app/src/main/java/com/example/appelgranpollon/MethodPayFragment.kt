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
import com.example.appelgranpollon.Models.*
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.adapters.CardsAdapter
import com.example.appelgranpollon.enums.StatusOrder
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MethodPayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MethodPayFragment : Fragment() {
    lateinit var client: ClientData
    lateinit var views:View;
   lateinit var  cart:CartData;
    lateinit var address:AddressData;
    lateinit var cards:ArrayList<CardData>
    lateinit var cardAdapter: CardsAdapter;
    lateinit var btnRealizarpedido:Button;
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
        views =  inflater.inflate(R.layout.fragment_method_pay, container, false)
        if(arguments?.getString("product") != null){
            address = Gson().fromJson(arguments?.getString("address")!!, AddressData::class.java);
            Log.d("LOGGING",address.toString())
        }
        btnRealizarpedido = views.findViewById(R.id.btnRealizarPedido);
        btnRealizarpedido.setOnClickListener {
            createOrder();
        }
        getCards()
        getCart()
        recyclerView = views.findViewById(R.id.RecyclerCards2)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager;
        cardAdapter = CardsAdapter(views.context,cards)
        recyclerView?.adapter = cardAdapter
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
    fun getCart(){
        val data =  SharedPrefs(views.context).getCart();
        cart = Gson().fromJson(data, CartData::class.java)

    }
    fun createOrder(){
       val order:OrderData = OrderData(cliente= ClientData(id = client.id), total =cart.total, cart = CartData(id=cart.id), address = AddressData(idAddress = address.idAddress), orderDate = "2022-12-12", status =StatusOrder.CONFIRMANDO.name  )
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).createOrder(order);
        val res = call.execute();

        try {
            Log.d("INFO",res.body().toString())
        }catch (t:Throwable){
            Log.d("INFO",t.message.toString())
        }
    }




}