package com.solapp.navtrip.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solapp.navtrip.R
import com.solapp.navtrip.ui.hotspots.Hotspot
import com.solapp.navtrip.ui.hotspots.HotspotAdapter

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val btnBack: ImageView = view.findViewById(R.id.btnBack)
        val rvFavorites: RecyclerView = view.findViewById(R.id.rvFavorites)
        val etSearch: EditText = view.findViewById(R.id.etSearch)

        val favoritesList = listOf(
            Hotspot("13th Flame Chaser", "Japanese food", "Most Authentic japanese food in surakarta city.....", "100 meters from you", R.drawable.splash),
            // tambahkan data lainnya jika mau
        )

        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        rvFavorites.adapter = HotspotAdapter(favoritesList)

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }
}
