package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.enums.TypeUser
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
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val inputEmail = view.findViewById<TextInputEditText>(R.id.inputEmailRegister);
        val inputPassword = view.findViewById<TextInputEditText>(R.id.inputPasswordRegister);
        val inputName = view.findViewById<TextInputEditText>(R.id.inputNameRegister);
        val inputLastname = view.findViewById<TextInputEditText>(R.id.inputLastNameRegister);
        val inputPhone = view.findViewById<TextInputEditText>(R.id.inputPhoneRegister)
        val inputDateOfBirth = view.findViewById<TextInputEditText>(R.id.inputDateOfbirthRegister);

        val txtLogin = view.findViewById<TextView>(R.id.txtLogin);
        val btnRegister = view.findViewById<Button>(R.id.btnRegister);
        btnRegister.setOnClickListener {
            val customer:ClientData = ClientData(email = inputEmail.text.toString(), password = inputPassword.text.toString(), name = inputName.text.toString(), lastname = inputLastname.text.toString(), phone = inputPhone.text.toString(), dateofbirth = inputDateOfBirth.text.toString())
            try {

//                val fromCustomerJson = Gson().toJson(customer);
//                val fromCUstomer = Gson().fromJson<ClientData>(fromCustomerJson,ClientData::class.java);
                val call = RestEngine.getRestEngine().create(ApiClient::class.java).registerClient(customer)
                call.enqueue(object : Callback<ClientData>{
                    override fun onFailure(call: Call<ClientData>, t: Throwable) {
                        Log.d("LOGGING","err")
                        Navigation.findNavController(view).navigate(R.id.loginFragment);
                    }

                    override fun onResponse(call: Call<ClientData>, response: Response<ClientData>) {
                        Log.d("LOGGING","sss")
                        Navigation.findNavController(view).navigate(R.id.loginFragment);
                    }
                });

            }finally{

            }

        }
        txtLogin.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }
        return view;
    }


}