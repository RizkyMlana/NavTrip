package com.solapp.navtrip.ui.hotspots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solapp.navtrip.R
import com.solapp.navtrip.databinding.FragmentHotspotsBinding

class HotspotsFragment : Fragment() {

    private var _binding: FragmentHotspotsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotspotsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val hotspotList = listOf(
            Hotspot("13th Flame Chaser", "Japanese food", "Most authentic japanese food in Surakarta city.....", "100 meters from you", R.drawable.splash),
            Hotspot("13th Flame Chaser", "Japanese food 1", "Most authentic japanese food in Surakarta city.....", "100 meters from you", R.drawable.splash),
            Hotspot("13th Flame Chaser", "Japanese food 2", "Most authentic japanese food in Surakarta city.....", "100 meters from you", R.drawable.splash),
            Hotspot("13th Flame Chaser", "Japanese food 3", "Most authentic japanese food in Surakarta city.....", "100 meters from you", R.drawable.splash),
            Hotspot("13th Flame Chaser", "Japanese food 4", "Most authentic japanese food in Surakarta city.....", "100 meters from you", R.drawable.splash),
            Hotspot("13th Flame Chaser", "Japanese food 5", "Most authentic japanese food in Surakarta city.....", "100 meters from you", R.drawable.splash),




            // tambah data dummy lainnya
        )

        binding.rvHotspots.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHotspots.adapter = HotspotAdapter(hotspotList)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}