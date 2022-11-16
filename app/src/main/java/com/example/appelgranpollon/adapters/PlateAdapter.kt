package com.example.appelgranpollon.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.PlateData
import com.example.appelgranpollon.R
import com.squareup.picasso.Picasso

class PlateAdapter(var context: Context, var arrayList: ArrayList<PlateData>):
    RecyclerView.Adapter<PlateAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_plate,parent,false)

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