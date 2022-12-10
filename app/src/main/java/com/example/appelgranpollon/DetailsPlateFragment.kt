package com.example.appelgranpollon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.appelgranpollon.Models.PlateData
import com.google.gson.Gson
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsPlateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsPlateFragment : Fragment() {
    lateinit var views:View;
    lateinit var product:PlateData;
    lateinit var detailsPlateImage:ImageView;
    lateinit var detailsPlateName:TextView;
    lateinit var detailsPlatePrice:TextView;
    lateinit var detailsPlateDescription:TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if(arguments?.getString("product") != null){
            product = Gson().fromJson(arguments?.getString("product")!!,PlateData::class.java);
            Log.d("LOGGING",product.toString())
        }
        views= inflater.inflate(R.layout.fragment_details_plate, container, false);
        detailsPlateName = views.findViewById<TextView>(R.id.detailsProductName);
        detailsPlateDescription = views.findViewById<TextView>(R.id.detailsProductDescription);
        detailsPlatePrice = views.findViewById<TextView>(R.id.detailsProductPrice)
        detailsPlateImage = views.findViewById<ImageView>(R.id.detailsProductImage)
        detailsPlateName.setText(product.name);
        detailsPlateDescription.setText(product.description)
        detailsPlatePrice.setText(product.price.toString())
        Picasso.with(views.context).load(product.image).fit().into(detailsPlateImage);

            return views;
    }


}