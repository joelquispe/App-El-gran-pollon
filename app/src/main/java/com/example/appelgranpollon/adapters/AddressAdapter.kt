package com.example.appelgranpollon.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.AddressData
import com.example.appelgranpollon.R
import com.google.android.material.card.MaterialCardView
class AddressAdapter (var context: Context, var arrayList: ArrayList<AddressData>):
    RecyclerView.Adapter<AddressAdapter.ItemHolder>(){

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_address,parent,false)
        itemHolder.findViewById<MaterialCardView>(R.id.cardAddress).setOnClickListener {

            var valor= getClickedPosition(itemHolder)
            Log.d("LOGGING", arrayList.get(valor).toString());
            var bundle: Bundle = Bundle();

            bundle.putString("address",arrayList.get(valor).idAddress.toString())
            Navigation.findNavController(itemHolder).navigate(R.id.methodPayFragment,bundle)



        }
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, i: Int) {
        var addressData: AddressData =arrayList.get(i)
        holder.name.text = addressData.address
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var name: TextView = itemView.findViewById<TextView>(R.id.name_address)
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