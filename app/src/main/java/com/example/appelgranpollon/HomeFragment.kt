package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.ToolbarWidgetWrapper
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.adapters.PlateAdapter
import com.google.android.material.navigation.NavigationView




class  HomeFragment : Fragment()   ,NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var drawerLayout:DrawerLayout ;
    lateinit var views:View;

    private var recyclerView: RecyclerView ?= null
    private var gridLayoutManager:GridLayoutManager ?= null
    private var arrayList:ArrayList<PlateData> ?= null
    private var plateAdapter:PlateAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views =  inflater.inflate(R.layout.fragment_home, container, false);

        initDrawer()
        recyclerView = views.findViewById(R.id.recyclerViewPlates)
        gridLayoutManager = GridLayoutManager(views.context,2,LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        arrayList = setDataInList()
        plateAdapter = PlateAdapter(views.context,arrayList!!)
        recyclerView?.adapter = plateAdapter
        return views;
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


    private fun setDataInList():ArrayList<PlateData>{

        var items:ArrayList<PlateData> = ArrayList()

        items.add(PlateData("Salchipapa Especial",R.drawable.choripapas,"S/16" ))
        items.add(PlateData("Parrilla Mediana",R.drawable.parrillas,"S/30" ))
        items.add(PlateData("Choripapa Especial",R.drawable.choripapas,"S/24" ))
        items.add(PlateData("Parrilla Familiar",R.drawable.parrillas,"S/60" ))


        return items

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item1-> drawerLayout.close()
            R.id.item2 -> Navigation.findNavController(views).navigate(R.id.categoryFragment);
            R.id.item3-> Navigation.findNavController(views).navigate(R.id.loginFragment);
            else ->{
                print("joel")
            }

        }
        return  false;
    }
}