package com.example.appelgranpollon.adapters

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
import com.example.appelgranpollon.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class CategoryAdapter (var context: Context, var arrayList: ArrayList<CategoryData>):
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_category,parent,false)
        itemHolder.findViewById<MaterialCardView>(R.id.cardCategory).setOnClickListener {

            var valor= getClickedPosition(itemHolder)
            Log.d("LOGGING", arrayList.get(valor).toString());
            var bundle:Bundle = Bundle();

            bundle.putString("category",arrayList.get(valor).name)
            Navigation.findNavController(itemHolder).navigate(R.id.homeFragment,bundle)



        }
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, i: Int) {
        var categoryData:CategoryData=arrayList.get(i)


        Picasso.with(context).load(categoryData.image).fit().into(holder.image);
        holder.name.text = categoryData.name
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var image: ImageView = itemView.findViewById<ImageView>(R.id.imagenCate)
        var name: TextView = itemView.findViewById<TextView>(R.id.tvNombre)
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