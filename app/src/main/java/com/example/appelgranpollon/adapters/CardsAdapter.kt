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
import com.example.appelgranpollon.Models.CardData
import com.example.appelgranpollon.R
import com.google.android.material.card.MaterialCardView

class CardsAdapter (var context: Context, var arrayList: ArrayList<CardData>):
    RecyclerView.Adapter<CardsAdapter.ItemHolder>(){

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_cards,parent,false)
        itemHolder.findViewById<MaterialCardView>(R.id.cardOrder).setOnClickListener {

            var valor= getClickedPosition(itemHolder)
            Log.d("LOGGING", arrayList.get(valor).toString());
            var bundle: Bundle = Bundle();

            bundle.putString("cards",arrayList.get(valor).toString())
            Navigation.findNavController(itemHolder).navigate(R.id.cardFragment,bundle)



        }
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, i: Int) {
        var cardData: CardData =arrayList.get(i)

        holder.numeroCard.text=cardData.number;
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var numeroCard: TextView = itemView.findViewById<TextView>(R.id.numCards)

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