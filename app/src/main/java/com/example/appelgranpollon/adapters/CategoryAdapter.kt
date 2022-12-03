package com.example.appelgranpollon.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appelgranpollon.Models.CategoryData
import com.example.appelgranpollon.R

class CategoryAdapter (var context: Context, var arrayList: ArrayList<CategoryData>):
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_category,parent,false)
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, i: Int) {
        var categoryData:CategoryData=arrayList.get(i)

        holder.image.setImageResource(categoryData.image!!)

        holder.name.text = categoryData.name
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var image = itemView.findViewById<ImageView>(R.id.imagenCate)
        var name = itemView.findViewById<TextView>(R.id.tvNombre)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}