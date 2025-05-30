package com.solapp.navtrip.ui.categories

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.solapp.navtrip.R
import com.solapp.navtrip.databinding.FragmentCategoriesBinding
import com.solapp.navtrip.model.HotspotItem
import com.solapp.navtrip.ui.adapter.HotspotAdapter

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HotspotAdapter
    private val hotspotList = mutableListOf<HotspotItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HotspotAdapter()
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesRecyclerView.adapter = adapter

        loadDummyData()

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filtered = hotspotList.filter {
                    it.title.contains(s.toString(), ignoreCase = true)
                }
                adapter.submitList(filtered)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadDummyData() {
        hotspotList.addAll(
            listOf(
                HotspotItem("13th Flame Chaser", "Japanese food", "100 meters from you", R.drawable.food_sample),
                HotspotItem("13th Flame Chaser", "Japanese food", "100 meters from you", R.drawable.food_sample),
                HotspotItem("13th Flame Chaser", "Japanese food", "100 meters from you", R.drawable.food_sample),
                // tambahkan data dummy lainnya...
            )
        )
        adapter.submitList(hotspotList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
