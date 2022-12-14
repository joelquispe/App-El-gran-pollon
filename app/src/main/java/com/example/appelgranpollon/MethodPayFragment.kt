package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    lateinit var idaddress:String;
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
        if(arguments?.getString("address") != null){

           idaddress = arguments?.getString("address").toString()

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
       val order:OrderData = OrderData(cliente= ClientData(id = client.id), total =cart.total, cart = CartData(id=cart.id), address = AddressData(idAddress = idaddress.toInt()), orderDate = "2022-12-12", status =StatusOrder.CONFIRMANDO.name  )
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).createOrder(order);
        call.enqueue(object:Callback<OrderData>{
            override fun onResponse(call: Call<OrderData>, response: Response<OrderData>) {
                Log.d("INFO",response.body().toString())

            }

            override fun onFailure(call: Call<OrderData>, t: Throwable) {
                Log.d("INFO",t.message.toString())
                editCart()
                SharedPrefs(views.context).removeCart();
                createCart()
                Navigation.findNavController(views).navigate(R.id.fragment_pago_Realizao);
            }
        })
    }
    fun editCart(){
        cart.isInOrder = true;
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).editCart(cart.id!!,cart)
        call.enqueue(object:Callback<CartData>{
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                Log.d("INFO",response.body().toString())
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                Log.d("INFO",t.message.toString())
            }
        })
    }
    fun createCart(){

        val client:ClientData = Gson().fromJson(SharedPrefs(views.context).getUser(),ClientData::class.java);
        val cartData:CartData = CartData(total = "0.00", isInOrder = false, cliente =ClientData(id = client.id) )
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).createCart(cartData);
        call.enqueue(object :Callback<CartData>{
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                Log.d("LOGGING","ssss")

            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                Log.d("LOGGING","errrr")
                getCart(client)
            }
        })


    }
    fun getCart(client:ClientData){
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).findCartNotOrder(client.id!!)
        val res = call.execute();
        try {
            val cart:CartData = Gson().getAdapter(CartData::class.java).fromJson(res.errorBody()?.string())
            SharedPrefs(views.context).saveCart(Gson().toJson(cart))

            Log.d("INFO","bien")
            Log.d("INFO",cart.toString())
        }catch (t:Throwable){
            Log.d("INFO","mal")
            Log.d("INFO",res.body().toString())

        }


    }


}