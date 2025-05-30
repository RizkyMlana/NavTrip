package com.solapp.navtrip.ui.destination

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solapp.navtrip.R
import com.solapp.navtrip.data.pref.DestinationModel

class DestinationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DestinationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerDestination)

        val destinationList = listOf(
            DestinationModel("13th Flame Chaser", "Japanese food", "Most Authentic Japanese food in surakarta city...", "100 meters from you", R.drawable.splash),
            DestinationModel("13th Flame Chaser", "Japanese food", "Most Authentic Japanese food in surakarta city...", "10 minutes from you", R.drawable.splash),
            DestinationModel("13th Flame Chaser", "Japanese food", "Most Authentic Japanese food in surakarta city...", "3 km from you", R.drawable.splash)
        )

        adapter = DestinationAdapter(destinationList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}
