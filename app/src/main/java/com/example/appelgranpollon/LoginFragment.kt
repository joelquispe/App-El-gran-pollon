package com.example.appelgranpollon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.appelgranpollon.Models.CartData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Models.MotorizedData
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.enums.TypeUser
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.gms.common.api.Api
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var viewOfLayout: View
    lateinit var navController:NavController;
    lateinit var fcmtokens:String;

    var isEmailValidate =false;
    var isPasswordValidate=false;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        viewOfLayout =inflater.inflate(R.layout.fragment_login, container, false)
        generateTokenFcm()
        val isMotorized = viewOfLayout.findViewById<SwitchMaterial>(R.id.typeUser);

        val boton:Button = viewOfLayout.findViewById<Button>(R.id.btnLogin);
        val inputEmail = viewOfLayout.findViewById<TextInputEditText>(R.id.inputEmail);
        val inputPassword = viewOfLayout.findViewById<TextInputEditText>(R.id.inputPassword);
        val txtRegister = viewOfLayout.findViewById<TextView>(R.id.txtRegister);
        boton.setOnClickListener (){
//            messagins()
            var typeUser = if(isMotorized.isChecked){
                TypeUser.Motorized.name;
            }else{
                TypeUser.Customer.name;
            }
            lateinit var client:ClientData;
            validateEmpty(inputEmail.text.toString(),inputPassword.text.toString(),viewOfLayout);
            if(isEmailValidate && isPasswordValidate){
                Log.d("LOGGING",typeUser)
                if(typeUser == TypeUser.Customer.name){
                    loginCustomer(typeUser,inputEmail,inputPassword);
                }else if(typeUser == TypeUser.Motorized.name){
                    loginMotorized(typeUser,inputEmail,inputPassword);
                }else{

                }

            }

        }
        txtRegister.setOnClickListener(){
            navController.navigate(R.id.registerFragment)
        }

        return viewOfLayout;
    }
    fun getUser(){
        var data =  SharedPrefs(viewOfLayout.context).getUser();
        Log.d("LOGGING",data)
        if(data != "null"){
            val client:ClientData = Gson().fromJson(data,ClientData::class.java)
            if(client != null){
                navController.navigate(R.id.homeFragment)
                Log.d("LOGGING",client.toString())
            }
        }

    }
    fun loginCustomer(typeUser:String,inputEmail:TextInputEditText,inputPassword: TextInputEditText){
        val call= RestEngine.getRestEngine().create(ApiClient::class.java).verifyClient(inputEmail.text.toString(),inputPassword.text.toString());
        call.enqueue(object : Callback<ClientData>{
            override fun onFailure(call: Call<ClientData>, t: Throwable) {
                Log.d("LOGGING",t.message.toString())
            }

            override fun onResponse(
                call: Call<ClientData>?,
                response: retrofit2.Response<ClientData>
            ) {
                try {

                    var client = Gson().getAdapter(ClientData::class.java).fromJson(response.errorBody()
                        ?.string());
                    client.fcmtoken = fcmtokens.toString();
                    refreshFcm(client);
                    Log.d("LOGGING",client.fcmtoken)
                    Log.d("LOGGING","ya funciona")
                    if(inputEmail.text.toString() == client.email && inputPassword.text.toString() == client.password){
                        //navegacion entre pantallas

                        SharedPrefs(viewOfLayout.context).saveTypeUser(typeUser);
                        SharedPrefs(viewOfLayout.context).saveUser(Gson().toJson(client));
                        getCart(client)
                        navController.navigate(R.id.homeFragment)

                    }
                }catch (e:Exception) {
                    Toast.makeText(context,"Usuario Invalido",Toast.LENGTH_SHORT).show();
                }

            }
        })
    }
    fun loginMotorized(typeUser:String,inputEmail:TextInputEditText,inputPassword: TextInputEditText){
        val call= RestEngine.getRestEngine().create(ApiClient::class.java).verifyMotorized(inputEmail.text.toString(),inputPassword.text.toString());
        call.enqueue(object : Callback<MotorizedData>{
            override fun onFailure(call: Call<MotorizedData>, t: Throwable) {
                Log.d("LOGGING",t.message.toString())
            }

            override fun onResponse(
                call: Call<MotorizedData>?,
                response: retrofit2.Response<MotorizedData>
            ) {
                try {
                    val motorized = Gson().getAdapter(MotorizedData::class.java).fromJson(response.errorBody()
                        ?.string());

                    Log.d("LOGGING",motorized.name)
                    if(inputEmail.text.toString() == motorized.email && inputPassword.text.toString() == motorized.password){
                        //navegacion entre pantallas
                        navController.navigate(R.id.motorizedHomeFragment)
                        SharedPrefs(viewOfLayout.context).saveTypeUser(typeUser);
                        SharedPrefs(viewOfLayout.context).saveUser(Gson().toJson(motorized));
                    }
                }catch (e:Exception) {
                    Toast.makeText(context,"Usuario Invalido",Toast.LENGTH_SHORT).show();
                }

            }
        })
    }
    private fun validateEmpty(email:String, password:String, view: View){
        val inputEmailLayout = view.findViewById<TextInputLayout>(R.id.inputEmailLayout);
        val inputPasswordLayout = view.findViewById<TextInputLayout>(R.id.inputPasswordLayout);
        val isEmailValid:Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if(email.isEmpty()){
            inputEmailLayout.error ="Llenar el campo de correo";
            inputEmailLayout.isErrorEnabled = true;
            isEmailValidate = false;
        }
        else if(!isEmailValid){
            inputEmailLayout.error ="Correo invalido";
            inputEmailLayout.isErrorEnabled = true;
            isEmailValidate = false;
        }
        else{
            inputEmailLayout.isErrorEnabled = false;
            isEmailValidate = true;
        }
        if(password.isEmpty()){
            inputPasswordLayout.error ="Llenar el campo de contraseña";
            inputPasswordLayout.isErrorEnabled = true;
            isPasswordValidate = false;
        }else{
            inputPasswordLayout.isErrorEnabled = false;
            isPasswordValidate = true;
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(viewOfLayout)

        getUser()
    }

    fun generateTokenFcm(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("info", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            fcmtokens = token.toString();
            Log.d("INFO",token.toString())

        })
    }
    fun getCart(client:ClientData){
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).findCartNotOrder(client.id!!)
        val res = call.execute();
        try {
            val cart: CartData = Gson().getAdapter(CartData::class.java).fromJson(res.errorBody()?.string())
            SharedPrefs(viewOfLayout.context).saveCart(Gson().toJson(cart))
            Log.d("INFO","bien")
            Log.d("INFO",cart.toString())
        }catch (t:Throwable){
            Log.d("INFO","mal")
            Log.d("INFO",res.body().toString())

        }


    }
    fun refreshFcm(cliente:ClientData){
        val call = RestEngine.getRestEngine().create(ApiClient::class.java).editClient(id = cliente.id!!, customer = cliente);
       call.enqueue(object:Callback<ClientData>{
           override fun onResponse(call: Call<ClientData>, response: Response<ClientData>) {
               Log.d("INFO",response.toString())
           }

           override fun onFailure(call: Call<ClientData>, t: Throwable) {
               Log.d("INFO","mal")
           }
       })

    }
}