package com.solapp.navtrip.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solapp.navtrip.R
import com.solapp.navtrip.data.pref.CategoryModel
import com.solapp.navtrip.data.pref.RecommendedModel
import com.solapp.navtrip.databinding.FragmentHomeBinding
import com.solapp.navtrip.ui.search.FilterDialogFragment

class HomeFragment : Fragment() {

    private lateinit var categoryRecycler: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var recommendedRecycler: RecyclerView
    private lateinit var recommendedAdapter: RecommendedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Hapus padding atas (status bar)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            v.setPadding(0, 0, 0, insets.systemGestureInsets.bottom)
            insets
        }


        return view
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Setup Recommended ---
        recommendedRecycler = view.findViewById(R.id.recycler_recommended)
        val recommendedList = listOf(
            RecommendedModel("Taman Balekambang", R.drawable.splash),
            RecommendedModel("Pasar Gede", R.drawable.splash),
            RecommendedModel("Keraton Surakarta", R.drawable.splash)
        )
        recommendedAdapter = RecommendedAdapter(recommendedList)
        recommendedRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recommendedRecycler.adapter = recommendedAdapter

        // --- Setup Category ---
        categoryRecycler = view.findViewById(R.id.recycler_categories)
        val categoryList = listOf(
            CategoryModel("Foods", R.drawable.foods),
            CategoryModel("Beverages", R.drawable.beverages),
            CategoryModel("Destinations", R.drawable.destinations)
        )
        categoryAdapter = CategoryAdapter(categoryList)
        categoryRecycler.layoutManager = LinearLayoutManager(requireContext())
        categoryRecycler.adapter = categoryAdapter

        val etSearch = view.findViewById<EditText>(R.id.et_search)
        etSearch.setOnClickListener {
            val dialog = FilterDialogFragment()
            dialog.show(parentFragmentManager, "FilterDialog")
        }

        // Setup tampilan dan status bar
        setupView()
        hideStatusBar()
    }


    private fun setupView() {
        val window = requireActivity().window
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideStatusBar() {
        requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
}

