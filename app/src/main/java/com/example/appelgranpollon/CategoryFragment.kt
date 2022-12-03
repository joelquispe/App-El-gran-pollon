package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CategoryData
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.adapters.CategoryAdapter
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.material.navigation.NavigationView


class CategoryFragment : Fragment() ,NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout:DrawerLayout ;
    lateinit var views:View;
    private var recyclerView: RecyclerView?= null
    private var gridLayoutManager: GridLayoutManager?= null
    private var arrayList:ArrayList<CategoryData> ?= null

    private var categoryAdapter:CategoryAdapter ?=null
    lateinit var allCategories:ArrayList<CategoryData> ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_category, container, false)
        getCategories();
        initDrawer()

        recyclerView = views.findViewById(R.id.RecyclerCategory)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        arrayList = allCategories;
        categoryAdapter = CategoryAdapter(views.context,arrayList!!)
        recyclerView?.adapter = categoryAdapter

        return views;
    }
    fun initDrawer(){
        val navigationView: NavigationView = views.findViewById<NavigationView>(R.id.navigationViewId);

        val toolbar = views.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarId);
        var menu = navigationView.menu;
        drawerLayout= views.findViewById<DrawerLayout>(R.id.drawerLayoutId)
        navigationView.getHeaderView(0);
        navigationView.bringToFront();
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this.activity,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    fun getCategories(){
        try{
            val call =  RestEngine.getRestEngine().create(ApiClient::class.java).findAllCategories();
            val response = call.execute();
            Log.d("LOGGING","asd"+response.toString());
            var categories:List<CategoryData>?  = response.body();
            if (categories != null) {
                allCategories = categories as ArrayList<CategoryData>;
            };
            Log.d("LOGGING",allCategories.toString());
        }finally{
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1-> Navigation.findNavController(views).navigate(R.id.homeFragment);
            R.id.item2 -> Navigation.findNavController(views).navigate(R.id.categoryFragment);
            R.id.item3-> Navigation.findNavController(views).navigate(R.id.loginFragment);
            else ->{
                print("joel")
            }
        }
        return  false;
    }
    private fun setDataInList():ArrayList<CategoryData>{

        var items:ArrayList<CategoryData> = ArrayList()
        items.add(CategoryData(1,"Parrilla","https://www.wikihow.com/images/f/f9/Make-a-Cheese-Sandwich-Step-21-Version-2.jpg"))
        items.add(CategoryData(2,"Espagueti","https://www.wikihow.com/images/f/f9/Make-a-Cheese-Sandwich-Step-21-Version-2.jpg"))
        items.add(CategoryData(3,"Carnes","https://www.wikihow.com/images/f/f9/Make-a-Cheese-Sandwich-Step-21-Version-2.jpg"))
        items.add(CategoryData(4,"Caldos","https://www.wikihow.com/images/f/f9/Make-a-Cheese-Sandwich-Step-21-Version-2.jpg"))
        items.add(CategoryData(5,"A la Brasa","https://www.wikihow.com/images/f/f9/Make-a-Cheese-Sandwich-Step-21-Version-2.jpg"))
        //items.add(PlateData("Parrilla Mediana",R.drawable.parrillas,"S/30" ))
        //items.add(PlateData("Choripapa Especial",R.drawable.choripapas,"S/24" ))
        //items.add(PlateData("Parrilla Familiar",R.drawable.parrillas,"S/60" ))


        return items


}}