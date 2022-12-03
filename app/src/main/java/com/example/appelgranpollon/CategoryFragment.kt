package com.example.appelgranpollon

import android.os.Bundle
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
import com.example.appelgranpollon.adapters.CategoryAdapter
import com.google.android.material.navigation.NavigationView


class CategoryFragment : Fragment() ,NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout:DrawerLayout ;
    lateinit var views:View;
    private var recyclerView: RecyclerView?= null
    private var gridLayoutManager: GridLayoutManager?= null
    private var arrayList:ArrayList<CategoryData> ?= null
    private var plateAdapter:CategoryAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_category, container, false)
        initDrawer()

        recyclerView = views.findViewById(R.id.RecyclerCategory)
        gridLayoutManager = GridLayoutManager(views.context,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        arrayList = setDataInList()
        plateAdapter = CategoryAdapter(views.context,arrayList!!)
        recyclerView?.adapter = plateAdapter

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
        items.add(CategoryData(1,"Parrilla",R.drawable.parrilla))
        items.add(CategoryData(2,"Espagueti",R.drawable.espagueti))
        items.add(CategoryData(3,"Carnes",R.drawable.carnes1))
        items.add(CategoryData(4,"Caldos",R.drawable.caldos))
        items.add(CategoryData(5,"A la Brasa",R.drawable.brasa))
        //items.add(PlateData("Parrilla Mediana",R.drawable.parrillas,"S/30" ))
        //items.add(PlateData("Choripapa Especial",R.drawable.choripapas,"S/24" ))
        //items.add(PlateData("Parrilla Familiar",R.drawable.parrillas,"S/60" ))


        return items


}}