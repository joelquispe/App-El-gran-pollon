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
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Services.SharedPrefs
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    lateinit var client:ClientData;
    lateinit var views:View;
    lateinit var  navController:NavController;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_profile, container, false)
        val btnAddress:Button = views.findViewById<Button>(R.id.addressBtn);
        val btnMethodPay:Button = views.findViewById<Button>(R.id.methodPayBtn);
        val btnAddCard:ImageButton = views.findViewById<ImageButton>(R.id.btnAddCard)
        val btnAddAddress:ImageButton = views.findViewById<ImageButton>(R.id.btnAddAddress)
        btnAddress.setOnClickListener {
            Log.d("LOGGING","err")
            navController.navigate(R.id.addressFragment)
        }
        btnMethodPay.setOnClickListener {
            Log.d("LOGGING","btn method pay")
            navController.navigate(R.id.addressFragment)
        }
        btnAddCard.setOnClickListener {
            navController.navigate(R.id.cardFragment)
        }
        btnAddAddress.setOnClickListener {
            navController.navigate(R.id.fragment_Agregar_Direccion)
        }
        getUser(views)
        return  views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view);
    }

    fun getUser(view:View){
        var data =  SharedPrefs(view.context).getUser();
        client = Gson().fromJson(data, ClientData::class.java)
        views.findViewById<TextView>(R.id.profileTxtName).text = client.name;
        views.findViewById<TextView>(R.id.profileTxtLastname).text = client.lastname;
        views.findViewById<TextView>(R.id.profileTxtPhone).text = client.phone;
        views.findViewById<TextView>(R.id.profileTxtEmail).text = client.email;
        views.findViewById<TextView>(R.id.profileTxtDateOfBirth).text = client.dateofbirth;
        if(client != null){
            Log.d("LOGGING",client.toString())
        }
    }

}