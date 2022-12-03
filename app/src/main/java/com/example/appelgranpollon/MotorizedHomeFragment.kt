package com.example.appelgranpollon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.appelgranpollon.Services.SharedPrefs
import com.example.appelgranpollon.databinding.FragmentMotorizedHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MotorizedHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MotorizedHomeFragment : Fragment() {

    private lateinit var viewfrag:View;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewfrag = inflater.inflate(R.layout.fragment_motorized_home, container, false);
        val button:Button = viewfrag.findViewById<Button>(R.id.button2);
        button.setOnClickListener {

        }
        return viewfrag;
    }
    fun logout(){
        SharedPrefs(this.viewfrag.context).removeUser();
        SharedPrefs(this.viewfrag.context).removeTypeUser();
        Navigation.findNavController(viewfrag).navigate(R.id.loginFragment);
    }


}