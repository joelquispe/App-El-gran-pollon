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
import com.example.appelgranpollon.Models.CartData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Models.MotorizedData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.enums.TypeUser
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

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

    lateinit var  customer:ClientData;
    lateinit var views:View;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_register, container, false)
        val inputEmail = views.findViewById<TextInputEditText>(R.id.inputEmailRegister);
        val inputPassword = views.findViewById<TextInputEditText>(R.id.inputPasswordRegister);
        val inputName = views.findViewById<TextInputEditText>(R.id.inputNameRegister);
        val inputLastname = views.findViewById<TextInputEditText>(R.id.inputLastNameRegister);
        val inputPhone = views.findViewById<TextInputEditText>(R.id.inputPhoneRegister)
        val inputDateOfBirth = views.findViewById<TextInputEditText>(R.id.inputDateOfbirthRegister);

        val txtLogin = views.findViewById<TextView>(R.id.txtLogin);
        val btnRegister = views.findViewById<Button>(R.id.btnRegister);
        btnRegister.setOnClickListener {
            customer = ClientData(email = inputEmail.text.toString(), password = inputPassword.text.toString(), name = inputName.text.toString(), lastname = inputLastname.text.toString(), phone = inputPhone.text.toString(), dateofbirth = inputDateOfBirth.text.toString())
            Log.d("LOGGING",customer.toString())

            try {
                val call = RestEngine.getRestEngine().create(ApiClient::class.java).registerClient(customer)

                val fromCustomerJson = Gson().toJson(customer);
                val fromCUstomer = Gson().fromJson<ClientData>(fromCustomerJson,ClientData::class.java);


                call.enqueue(object : Callback<ClientData>{
                    override fun onFailure(call: Call<ClientData>, t: Throwable) {

                        login()

                    }

                    override fun onResponse(call: Call<ClientData>, response: Response<ClientData>) {
                        Log.d("LOGGING","sss")
                        Navigation.findNavController(views).navigate(R.id.loginFragment);
                    }
                });


            }catch (t:Throwable){
                Log.d("LOGGING",t.toString())
            }

        }

        txtLogin.setOnClickListener(){
            Navigation.findNavController(views).navigate(R.id.loginFragment);
        }
        return views;
    }

    fun createCart(){

        val client:ClientData = Gson().fromJson(SharedPrefs(views.context).getUser(),ClientData::class.java);
        val cartData:CartData = CartData(total = "0.00", isInOrder = false, cliente =ClientData(id = client.id) )
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).createCart(cartData);
        call.enqueue(object :Callback<CartData>{
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                Log.d("LOGGING","ssss")
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                Log.d("LOGGING","errrr")
            }
        })


    }

    fun login(){
        val call= RestEngine.getRestEngine().create(ApiClient::class.java).verifyClient(customer.email,customer.password);
        val res = call.execute();
        try{

            val customerr = Gson().getAdapter(ClientData::class.java).fromJson(res.errorBody()?.string());
            Log.d("LOGGING",customerr.toString())
            SharedPrefs(views.context).saveTypeUser(TypeUser.Customer.name);
            SharedPrefs(views.context).saveUser(Gson().toJson(customerr));
            createCart();
            Navigation.findNavController(views).navigate(R.id.homeFragment);
        }catch (t:Throwable){
            val customerr = Gson().getAdapter(ClientData::class.java).fromJson(res.errorBody()?.string());
            SharedPrefs(views.context).saveTypeUser(TypeUser.Customer.name);
            SharedPrefs(views.context).saveUser(Gson().toJson(customerr));
            Navigation.findNavController(views).navigate(R.id.homeFragment);

        }
    }


}