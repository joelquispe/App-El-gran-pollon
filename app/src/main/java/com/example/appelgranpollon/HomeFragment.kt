package com.example.appelgranpollon

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.KeyEvent;
import android.widget.Button

import android.widget.LinearLayout
import android.widget.TextView

import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.ToolbarWidgetWrapper
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CartData
import com.example.appelgranpollon.Models.CategoryData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.adapters.PlateAdapter
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine

import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.util.Arrays


class  HomeFragment : Fragment()   ,NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters

    lateinit var inputSearch:TextInputEditText ;
    lateinit var drawerLayout:DrawerLayout ;
    lateinit var textCategory:String;
    lateinit var views:View;
    lateinit var name:TextView;
    lateinit var client:ClientData;
    lateinit var cart: CartData;
    private var recyclerView: RecyclerView ?= null
    private var gridLayoutManager:GridLayoutManager ?= null
    private var arrayList:ArrayList<PlateData> ?= null
    private var plateAdapter:PlateAdapter ?=null
    lateinit var allProducts:ArrayList<PlateData> ;
    lateinit var  filterProducts:ArrayList<PlateData>;
    lateinit var navController: NavController;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views =  inflater.inflate(R.layout.fragment_home, container, false);
        getProducts()

        var texto = getString(R.string.nameUser);


        inputSearch = views.findViewById<TextInputEditText>(R.id.inputSearch);
        filterProducts = ArrayList();
        inputSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Log.d("INFO",s.toString());
//                if (s != null) {
//                    if(s.isBlank()){
//                        filterProducts = allProducts;
//                        plateAdapter!!.notifyDataSetChanged()
//                        Log.d("INFO",allProducts.toString());
//                        Log.d("INFO",filterProducts.toString());
//                    }
//                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable?) {
                filterProducts.clear()
                Log.d("LOGGING","allproduct"+allProducts.toString())
                for(p in allProducts){
                    Log.d("LOGGING",p.name.compareTo(s.toString()).toString())
                    if(p.category.name == s.toString() || p.name.compareTo(s.toString())==0){
                        filterProducts.add(p)
                        plateAdapter!!.notifyDataSetChanged()
                        Log.d("LOGGING",p.toString())
                    }
                }

                plateAdapter!!.notifyDataSetChanged()

            }

        })
        var btnCart:FloatingActionButton = views.findViewById<FloatingActionButton>(R.id.btnCart);
        btnCart.setOnClickListener(){
            navigate(views);
        }
        initDrawer()
        recyclerView = views.findViewById(R.id.recyclerViewPlates)
        gridLayoutManager = GridLayoutManager(views.context,2,LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        arrayList = allProducts
        filterProducts.addAll(allProducts)
        plateAdapter = PlateAdapter(views.context,filterProducts)
        recyclerView?.adapter = plateAdapter
        name = views.findViewById(R.id.txtTile)
        getUser(views)
        if(arguments?.getString("category") != null){
            textCategory = arguments?.getString("category")!!;
            inputSearch.setText(textCategory)  ;
        }
        return views;
    }
    fun navigate(view: View){
        Navigation.findNavController(view).navigate(R.id.shoppingCartFragment);
    }
    fun getUser(view:View){
        var data =  SharedPrefs(view.context).getUser();
        client = Gson().fromJson(data, ClientData::class.java)
        name.text = "Hola "+client.name
        if(client != null){
            Log.d("LOGGING",client.toString())
        }

    }
    fun getCart(view:View){
        val data =  SharedPrefs(view.context).getCart();
        cart = Gson().fromJson(data, CartData::class.java)

    }

    fun initDrawer(){
        val navigationView:NavigationView = views.findViewById<NavigationView>(R.id.navigationViewId);
        val toolbar = views.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarId);
        var menu = navigationView.menu;
        drawerLayout= views.findViewById<DrawerLayout>(R.id.drawerLayoutId)
        navigationView.getHeaderView(0);
        navigationView.bringToFront();
        val toggle:ActionBarDrawerToggle = ActionBarDrawerToggle(this.activity,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }
    fun getProducts(){
        try{
            val call =  RestEngine.getRestEngine().create(ApiClient::class.java).findAllProducts();
            val response = call.execute();

            var products:List<PlateData>?  = response.body();
            if (products != null) {
                allProducts = products as ArrayList<PlateData>;
            };
            Log.d("LOGGING",allProducts.toString());
        }finally{
        }
    }
/*
    private fun setDataInList():ArrayList<PlateData>{

        var items:ArrayList<PlateData> = ArrayList()
        items.add(PlateData(1,"Salchipapa Especial",20.0,2,"rico",true,"https://i.blogs.es/0e55d6/sandwich-mixto-rec/840_560.jpg", CategoryData(1,"sandwich","image")))
        items.add(PlateData(2,"Salchipapa Especial",20.0,2,"rico",true,"https://i.blogs.es/0e55d6/sandwich-mixto-rec/840_560.jpg", CategoryData(1,"sandwich","image")))
        items.add(PlateData(3,"Salchipapa Especial",20.0,2,"rico",true,"https://i.blogs.es/0e55d6/sandwich-mixto-rec/840_560.jpg", CategoryData(1,"sandwich","image")))
        //items.add(PlateData("Parrilla Mediana",R.drawable.parrillas,"S/30" ))
        //items.add(PlateData("Choripapa Especial",R.drawable.choripapas,"S/24" ))
        //items.add(PlateData("Parrilla Familiar",R.drawable.parrillas,"S/60" ))


        return items
    }
*/
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item1-> drawerLayout.close()
            R.id.perfil->Navigation.findNavController(views).navigate(R.id.profileFragment)
            R.id.item2 -> Navigation.findNavController(views).navigate(R.id.categoryFragment);
            R.id.drawerOrders-> Navigation.findNavController(views).navigate(R.id.ordersFragment);
            R.id.item3-> logout()
            else ->{
                print("joel")
            }

        }
        return  false;
    }
    fun logout(){
        SharedPrefs(this.views.context).removeUser();
        SharedPrefs(this.views.context).removeTypeUser();
        SharedPrefs(this.views.context).removeCart();
        Navigation.findNavController(views).navigate(R.id.loginFragment);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(views)
    }



}