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
import com.google.android.material.navigation.NavigationView


class CategoryFragment : Fragment() ,NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout:DrawerLayout ;
    lateinit var views:View;

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


}