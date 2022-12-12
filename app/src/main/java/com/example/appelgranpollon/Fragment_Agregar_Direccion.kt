package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.appelgranpollon.Models.AddressData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_Agregar_Direccion : Fragment(), OnMapReadyCallback , GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{
    lateinit var client: ClientData
    lateinit var views:View;
    lateinit var inputAddress: TextInputEditText;
    lateinit var inputCity: TextInputEditText;
    lateinit var inputNumber: TextInputEditText;
    lateinit var inputReference: TextInputEditText;
    lateinit var inputStreet: TextInputEditText;
    lateinit var btnSaveAddress: Button;
    lateinit var longitude:String;
    lateinit var latitude:String;
    lateinit var mMap:GoogleMap;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views =  inflater.inflate(R.layout.fragment__agregar__direccion, container, false)
        getUser(views)
createMapFragment()
        inputAddress = views.findViewById<TextInputEditText>(R.id.inputDirection)
        inputCity = views.findViewById<TextInputEditText>(R.id.inputCity)
        inputNumber = views.findViewById<TextInputEditText>(R.id.inputNumber)
        inputReference = views.findViewById<TextInputEditText>(R.id.inputReference)
        inputStreet = views.findViewById<TextInputEditText>(R.id.inputStreet)
        btnSaveAddress = views.findViewById<Button>(R.id.btnSaveAddress)


        btnSaveAddress.setOnClickListener {
            Log.d("LOGGING",client.toString())
            createAddress()
        }
        return  views;
    }
    private fun createMapFragment(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment;
        mapFragment.getMapAsync(this);
    }
    private fun getUser(view:View){
        var data =  SharedPrefs(view.context).getUser();
        client = Gson().fromJson(data, ClientData::class.java)

        if(client != null){
            Log.d("LOGGING",client.toString())
        }
    }
    private fun createAddress(){
        val addresss = AddressData(address = inputAddress.text.toString(), number = inputNumber.text.toString(), street = inputStreet.text.toString(), city = inputCity.text.toString(), reference = inputReference.text.toString(), cliente = ClientData(id = client.id), latitude = latitude, longitude = longitude);
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).createAddress(address = addresss);
        call.enqueue(object: Callback<AddressData> {
            override fun onResponse(call: Call<AddressData>, response: Response<AddressData>) {
                Log.d("LOGGING",response.toString())
                Navigation.findNavController(views).navigate(R.id.profileFragment);
            }

            override fun onFailure(call: Call<AddressData>, t: Throwable) {
                Log.d("LOGGING","err")
                Navigation.findNavController(views).navigate(R.id.profileFragment);
            }
        })
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0;
        this.mMap.setOnMapClickListener(this)
        this.mMap.setOnMapLongClickListener(this)

        val initialPosition = LatLng(-12.07469232190916, -77.06610142422456)
        mMap.addMarker(MarkerOptions().position(initialPosition).title("ME"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialPosition))
    }

    override fun onMapClick(p0: LatLng) {
        longitude = p0.longitude.toString()
        latitude = p0.latitude.toString()
        mMap.clear();
        var positionCustom = LatLng(p0.latitude, p0.longitude)

        mMap.addMarker(MarkerOptions().position(positionCustom).title("ME"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(positionCustom))
    }

    override fun onMapLongClick(p0: LatLng) {
        TODO("Not yet implemented")
    }


}