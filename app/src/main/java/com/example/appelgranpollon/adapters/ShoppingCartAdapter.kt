package com.example.appelgranpollon.adapters;

import android.content.Context;
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.example.appelgranpollon.Models.CartITemData;
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.R
import com.example.appelgranpollon.network.ApiClient
import com.example.appelgranpollon.network.RestEngine
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList;

class ShoppingCartAdapter (var context:Context, var arrayList:ArrayList<CartITemData>):
        RecyclerView.Adapter<ShoppingCartAdapter.ItemHolder>(){




        class ItemHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById<TextView>(R.id.txtProducto)
            var precio: TextView = itemView.findViewById<TextView>(R.id.txtPrecio)
            var quantity: TextView = itemView.findViewById<TextView>(R.id.txtCantidad)
                var image:ImageView = itemView.findViewById<ImageView>(R.id.imageCartItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
                val itemHolder = LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_order_detail,parent,false)

                itemHolder.findViewById<ImageButton>(R.id.btnAddQuantity).setOnClickListener {
                        var itemData: CartITemData = arrayList.get(getClickedPosition(itemHolder));
                        Log.d("INFO",itemData.toString())
                        itemData.quantity = (itemData.quantity.toInt() + 1).toString();
                        editQuantity(itemData.idCartItem!!,itemData)
                        notifyItemChanged(getClickedPosition(itemHolder))
                }
                itemHolder.findViewById<ImageButton>(R.id.btnMinusQuantity).setOnClickListener {
                        var itemData: CartITemData = arrayList.get(getClickedPosition(itemHolder));
                        itemData.quantity = (itemData.quantity.toInt() - 1).toString();
                        editQuantity(itemData.idCartItem!!,itemData)
                        notifyItemChanged(getClickedPosition(itemHolder))
                }
                return ShoppingCartAdapter.ItemHolder(itemHolder)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
                var cartITemData: CartITemData =arrayList.get(position)
                Picasso.with(context).load(cartITemData.product?.image).fit().into(holder.image);
                holder.name.text = cartITemData.product!!.name.toString()
                holder.precio.text = "S/"+cartITemData.product!!.price.toString()
                holder.quantity.text = cartITemData.quantity.toString()
        }

        override fun getItemCount(): Int {
                return  arrayList.size;
        }

        private fun getClickedPosition(clickedView: View): Int {
                val recyclerView = clickedView.parent as RecyclerView
                val currentViewHolder = recyclerView.getChildViewHolder(clickedView)
                return currentViewHolder.getAdapterPosition()
        }
        fun editQuantity(id:Int,item:CartITemData){
                val call = RestEngine.getRestEngine().create(ApiClient::class.java).editQuantityItem(id,item)
                call.enqueue(object:Callback<CartITemData>{
                        override fun onResponse(call: Call<CartITemData>, response: Response<CartITemData>) {
                                Log.d("INFO",response.body().toString())
                                Log.d("INFO","bien")
                        }

                        override fun onFailure(call: Call<CartITemData>, t: Throwable) {
                                Log.d("INFO",t.message.toString())
                                Log.d("INFO","aml")
                        }

                })
        }

}
