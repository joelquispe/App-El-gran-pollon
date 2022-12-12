package com.example.appelgranpollon.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CategoryData
import com.example.appelgranpollon.Models.OrderData
import com.example.appelgranpollon.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class OrdersAdapter (var context: Context, var arrayList: ArrayList<OrderData>):
    RecyclerView.Adapter<OrdersAdapter.ItemHolder>(){

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_order,parent,false)
        itemHolder.findViewById<MaterialCardView>(R.id.cardOrder).setOnClickListener {

            var valor= getClickedPosition(itemHolder)
            Log.d("LOGGING", arrayList.get(valor).toString());
            var bundle: Bundle = Bundle();

            bundle.putString("Order",arrayList.get(valor).toString())
            Navigation.findNavController(itemHolder).navigate(R.id.ordersFragment,bundle)



        }
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, i: Int) {
        var ordeData: OrderData =arrayList.get(i)


        holder.cliente.text=ordeData.cliente.toString();
        holder.direccion.text=ordeData.address.toString();
        holder.hora.text=ordeData.order_date.toString();
        holder.estado.text=ordeData.status.toString();
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var cliente: TextView = itemView.findViewById<TextView>(R.id.nomCliente)
        var direccion: TextView = itemView.findViewById<TextView>(R.id.nomdireccion)
        var hora: TextView = itemView.findViewById<TextView>(R.id.horaPEdi)
        var estado: TextView = itemView.findViewById<TextView>(R.id.EstadoPedi)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    private fun getClickedPosition(clickedView: View): Int {
        val recyclerView = clickedView.parent as RecyclerView
        val currentViewHolder = recyclerView.getChildViewHolder(clickedView)
        return currentViewHolder.getAdapterPosition()
    }

}