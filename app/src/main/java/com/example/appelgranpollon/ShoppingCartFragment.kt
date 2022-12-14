package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CartData
import com.example.appelgranpollon.Models.CartITemData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.adapters.OrdersAdapter
import com.example.appelgranpollon.adapters.ShoppingCartAdapter
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingCartFragment : Fragment() {
    lateinit var navController: NavController;
    lateinit var views:View;
    lateinit var cart:CartData;
    var total:Double =0.0;
     var subtotal:Double=0.0;
    lateinit var cardItems :ArrayList<CartITemData>
    lateinit var cartAdapter: ShoppingCartAdapter;
    private var recyclerView: RecyclerView?= null
    private var gridLayoutManager: GridLayoutManager?= null
    lateinit var txtTotal:TextView;
    lateinit var txtSubtotal:TextView;
    lateinit var btnNext:Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        views = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        txtSubtotal = views.findViewById(R.id.cartSubtotal);
        txtTotal = views.findViewById(R.id.cartTotal);
        getItemOfCustomer()
        btnNext = views.findViewById(R.id.btnNext);
        val backbutton:ImageButton = views.findViewById<ImageButton>(R.id.imageButton)
        recyclerView = views.findViewById(R.id.RecyclerCart)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager;
        cartAdapter = ShoppingCartAdapter(views.context,cardItems)
        recyclerView?.adapter = cartAdapter

        backbutton.setOnClickListener {
            navController.navigate(R.id.homeFragment);
        }
        btnNext.setOnClickListener {
            editCart()
            navController.navigate(R.id.addressFragment);
        }

        return  views;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
    fun getItemOfCustomer(){
        cart = Gson().fromJson(SharedPrefs(views.context).getCart(),CartData::class.java);

        val call = RestEngine.getRestEngine().create(ApiClient::class.java).getCartItemByCart(id = cart.id!!)
        val res = call.execute();

        try{
            val listCartItems:List<CartITemData>? = res.body();
            if(res.body().toString() == "null"){
                cardItems = ArrayList();
            }
            Log.d("INFO",res.body().toString())
            if(listCartItems != null){
                cardItems =ArrayList(listCartItems);
            }

            Log.d("INFO",res.body().toString());

        }catch (t:Throwable){
            Log.d("INFO",t.message.toString())
        }
        getDataCart()

    }

    fun getDataCart(){
        if(cardItems.isNotEmpty()){
            for(c in cardItems){
                subtotal += c.product!!.price.toDouble() * c.quantity.toInt()

            }
            total = subtotal + 10.0;
            txtTotal.text = "S/"+total.toString();
            txtSubtotal.text ="S/"+subtotal.toString();
        }

    }
    fun editCart(){
        cart.total = total.toString();
        Log.d("INFO",cart.toString())
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).editCart(cart.id!!,cart)
        call.enqueue(object: Callback<CartData> {
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                Log.d("INFO","bien")
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                Log.d("INFO","mal")
                SharedPrefs(views.context).saveCart(Gson().toJson(cart))
            }
        })
    }

}