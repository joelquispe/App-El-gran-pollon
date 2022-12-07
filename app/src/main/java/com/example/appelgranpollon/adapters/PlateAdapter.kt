package com.example.appelgranpollon.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CartData
import com.example.appelgranpollon.Models.CartITemData
import com.example.appelgranpollon.Models.ClientData
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.R
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlateAdapter(var context: Context, var arrayList: ArrayList<PlateData>):
    RecyclerView.Adapter<PlateAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_plate,parent,false)
        itemHolder.findViewById<Button>(R.id.btnAdd).setOnClickListener {

            val product:PlateData = arrayList.get(viewType);
            Log.d("LOGGING",product.toString());

            val cartt:CartData = CartData(id = 3)
//            val client:ClientData = Gson().fromJson(SharedPrefs(parent.context).getUser(),ClientData::class.java) ;
//            var cartItem:CartITemData = CartITemData(quantity = "1", product = product, cart = cartt  );
//            Log.d("LOGGING",cartItem.toString());
//            val call = RestEngine.getRestEngine().create(ApiClient::class.java).addItem(cartItem);
//            call.enqueue(object : Callback<CartITemData>{
//                override fun onResponse(call: Call<CartITemData>, response: Response<CartITemData>) {
//                    Log.d("LOGGING",response.toString());
//                }
//
//                override fun onFailure(call: Call<CartITemData>, t: Throwable) {
//                    Log.d("LOGGING","error");
//                }
//
//            })
            var cartItemQuantity:CartITemData = CartITemData(quantity = "2");
            val callquantity = RestEngine.getRestEngine().create(ApiClient::class.java).editQuantityItem(3,cartItemQuantity);
            callquantity.enqueue(object : Callback<CartITemData>{
                override fun onResponse(call: Call<CartITemData>, response: Response<CartITemData>) {
                    Log.d("LOGGING",response.toString());
                }

                override fun onFailure(call: Call<CartITemData>, t: Throwable) {
                    Log.d("LOGGING","error");
                }

            })
        }
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var plateData:PlateData = arrayList.get(position)
        Picasso.with(context).load(plateData.image).fit().into(holder.image);


        holder.name.text = plateData.name
        holder.price.text = "S/"+plateData.price.toString()



    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var image = itemView.findViewById<ImageView>(R.id.image_plate)
        var name = itemView.findViewById<TextView>(R.id.name_plate)
        var price = itemView.findViewById<TextView>(R.id.price_plate)


    }


}