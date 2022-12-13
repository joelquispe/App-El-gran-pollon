package com.example.appelgranpollon.adapters;

import android.content.Context;
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.example.appelgranpollon.Models.CartITemData;
import com.example.appelgranpollon.R
import com.google.android.material.card.MaterialCardView

import java.util.ArrayList;

class ShoppingCartAdapter (var context:Context, var arrayList:ArrayList<CartITemData>):
        RecyclerView.Adapter<ShoppingCartAdapter.ItemHolder>(){




        class ItemHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById<TextView>(R.id.txtProducto)
            var precio: TextView = itemView.findViewById<TextView>(R.id.txtPrecio)
            var quantity: TextView = itemView.findViewById<TextView>(R.id.txtCantidad)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
                val itemHolder = LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_order_detail,parent,false)
                itemHolder.findViewById<MaterialCardView>(R.id.cardOrderDetail).setOnClickListener {

                        var valor= getClickedPosition(itemHolder)
                        Log.d("LOGGING", arrayList.get(valor).toString());
                        var bundle: Bundle = Bundle();


                        Navigation.findNavController(itemHolder).navigate(R.id.shoppingCartFragment,bundle)



                }
                return ShoppingCartAdapter.ItemHolder(itemHolder)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
                var cartITemData: CartITemData =arrayList.get(position)
                holder.name.text = cartITemData.product!!.name.toString()
                holder.precio.text = cartITemData.product!!.price.toString()
                holder.quantity.text = cartITemData.quantity.toString()
        }

        override fun getItemCount(): Int {
                TODO("Not yet implemented")
        }

        private fun getClickedPosition(clickedView: View): Int {
                val recyclerView = clickedView.parent as RecyclerView
                val currentViewHolder = recyclerView.getChildViewHolder(clickedView)
                return currentViewHolder.getAdapterPosition()
        }

}
