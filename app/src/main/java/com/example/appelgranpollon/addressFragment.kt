package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.appelgranpollon.Models.AddressData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.material.textfield.TextInputEditText
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
 * Use the [addressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addressFragment : Fragment() {

    lateinit var views:View;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_address, container, false)

        return  views;
    }


}